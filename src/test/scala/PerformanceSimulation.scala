import com.typesafe.config.ConfigFactory
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import pages.UserActions
import utils.PerformanceConfig

import scala.concurrent.duration._

class PerformanceSimulation extends Simulation with PerformanceConfig{

  val userSet1 : Int = ConfigFactory.load().getInt("userSet1")
  val userSet2 : Int = ConfigFactory.load().getInt("userSet2")
  val timeInSeconds : Int = ConfigFactory.load().getInt("timeInMinutes")

  //Defining scenarios
  val userScenarios: ScenarioBuilder =
    scenario("User performs")
      .group("Flight Booking Actions") (
        exec(
          UserActions.view_landing_page(),
          UserActions.navigate_to_rservation_page(),
          UserActions.navigate_to_purchase_page()
        )
    )

  //Setting up execution
  setUp(
    userScenarios.inject(rampUsers(userSet1) over (timeInSeconds second)).protocols(httpProtocol)
  )
    .assertions(global.failedRequests.  count.is(0))
    .assertions(global.responseTime.max.lt(3000))
}
