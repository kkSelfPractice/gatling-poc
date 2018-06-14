import com.typesafe.config.ConfigFactory
import io.gatling.core.Predef._
import io.gatling.core.feeder.RecordSeqFeederBuilder
import io.gatling.core.structure.ScenarioBuilder
import pages.UserActions
import utils.PerformanceConfig

import scala.concurrent.duration._

class PerformanceSimulation extends Simulation with PerformanceConfig {

  val browsingUsers: Int = ConfigFactory.load().getInt("browsingUsers")
  val buyingUsers: Int = ConfigFactory.load().getInt("buyingUsers")
  val timeInSeconds: Int = ConfigFactory.load().getInt("timeInSeconds")

  val dataFeeder: RecordSeqFeederBuilder[String] = csv("dataFeeder.csv").random

  //Defining scenarios
  val buyerScenario: ScenarioBuilder =
    scenario("Buyer Scenario")
      .group("Buyer User Actions")(
        exec(
          UserActions.view_landing_page(),
          UserActions.navigate_to_rservation_page("Paris","Buenos Aires"),
          UserActions.navigate_to_purchase_page()
        )
      )

  val browsingScenario: ScenarioBuilder =
    scenario("Browsing Scenario")
      .feed(dataFeeder)
      .group("Browsing User Actions")(
        exec(
          UserActions.view_landing_page(),
          UserActions.navigate_to_rservation_page("${fromPort}","${toPort}")
        )
      )

  val repeatedBrowsingScenario: ScenarioBuilder =
    scenario("Random Browsing Scenario")
      .group("Repeated Browsing User Actions")(
        repeat(7, "n"){
        exec(
          UserActions.view_landing_page(),
          UserActions.navigate_to_rservation_page("Paris","Buenos Aires")
        )}
      )

  //Setting up execution
  setUp(
    browsingScenario.inject(rampUsers(browsingUsers) over (timeInSeconds second)).protocols(httpProtocol),
    buyerScenario.inject(rampUsers(buyingUsers) over (timeInSeconds second)).protocols(httpProtocol),
    repeatedBrowsingScenario.inject(rampUsers(1) over (timeInSeconds second)).protocols(httpProtocol)
  )
    .assertions(global.failedRequests.count.is(0))
    .assertions(global.responseTime.max.lt(3000))
}
