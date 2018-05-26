package utils

import com.typesafe.config.ConfigFactory
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocol

trait PerformanceConfig {
  
  def httpProtocol : HttpProtocol = http
    .baseURL(getUrl)
    .acceptHeader("*/*")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .disableCaching
    .disableWarmUp
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36")
    .build
  val javascriptHeaders = Map("""Accept""" -> """application/json, text/javascript, */*""")

  val plainHeaders = Map("""Accept""" -> """application/json, text/plain, */*""")

  val getUrl: String = ConfigFactory.load().getString("appUrl")
}