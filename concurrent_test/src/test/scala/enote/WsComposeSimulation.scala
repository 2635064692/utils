package enote

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt

/**
 * 优惠券领取压测
 */
class WsComposeSimulation extends Simulation {

  /**
   * 设置请求的根路径
   */
  val httpConf = http.
    baseUrl("http://127.0.0.1:9716")
    .acceptHeader("*/*")
    .contentTypeHeader("application/json")

  /**
    *设置请求的方式和路径并命名此请求名称，建议请求名称全局唯一
    */
  object HttpDemo {

//    val feeder = csv("E:\\sefl-git\\utils\\concurrent_test\\src\\test\\resources\\data\\loadPage_data.csv").queue
    val Content_type = Map("Content-Type" -> "application/json") //定义header
    val demo = exec(
      http("workspaceWebHost")
        .get("/WorkspacesWebService/semaphore/test")
    )

//    val demo =
//      exec(
//      http("workspaceWebHost")
//        .post("/WorkspacesWebService/record/loadPageData")
////        .body(StringBody("{\"pageId\":\"${id}\",\"limit\":50,\"cursor\":{\"stack\":[[{\"table\":\"block\",\"id\":\"${id}\",\"index\":0}]]},\"clientId\":\"${clientId}\",\"chunkNumber\":0,\"verticalColumns\":false}"))
//        .body(StringBody("{\"pageId\":\"0273dc4c-61ca-48fb-9072-bf84ddba3321\",\"limit\":50,\"cursor\":{\"stack\":[[{\"table\":\"block\",\"id\":\"0273dc4c-61ca-48fb-9072-bf84ddba3321\",\"index\":0}]]},\"clientId\":\"bDd9o615r0TTf6Fidh5V6ZYfF4SBiuhY\",\"chunkNumber\":0,\"verticalColumns\":false}"))
//        .headers(Content_type)
//        .asJson
//        .asFormUrlEncoded
//        .check(status.is(200))
//    )
  }

  /**
  设置请求属于归属方案
   */
  val scn = scenario("loadPageDataSimulation").exec(HttpDemo.demo)

  //设置请求场景 constantUsersPerSec(10) during(20 seconds) randomized
  //以固定的速度模拟用户，指定每秒模拟的用户数(10)，指定模拟时间段(20 seconds)。用户数将在随机被随机模拟（毫秒级别）。
  setUp(
    scn.inject(constantUsersPerSec(30).during(10.seconds).randomized),
  ).protocols(httpConf)
}