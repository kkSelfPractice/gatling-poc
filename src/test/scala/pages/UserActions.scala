package pages

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import utils.PerformanceConfig

object UserActions extends PerformanceConfig{

  def view_landing_page(): ChainBuilder = exec(http(s"Open BlazeDemo Landing page")
    .get(s"/")
    .headers(javascriptHeaders)
    .check(status.is(200))
  )

  def navigate_to_rservation_page(): ChainBuilder = exec(http(s"Navigation to Flight Reservation page")
    .post(s"/reserve.php")
      .formParamMap(Map("fromPort"->"Paris","toPort"->"Buenos Aires"))
    .headers(javascriptHeaders)
    .check(status.is(200))
  )

  def navigate_to_purchase_page(): ChainBuilder = exec(http(s"Navigation to Purchase page")
    .post(s"/purchase.php")
    .formParamMap(Map("flight"->"43","price"->"472.56","airline"->"Virgin America","fromPort"->"Paris","toPort"->"Buenos Aires"))
    .headers(javascriptHeaders)
    .check(status.is(200))
  )

}
