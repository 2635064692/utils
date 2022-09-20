package enote

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt

/**
 * 优惠券领取压测
 */
class LoadRecordSimulation extends Simulation {

  /**
   * 设置请求的根路径
   */
  val httpConf = http.
    baseUrl("http://127.0.0.1:9716")
    .acceptHeader("*/*")
    .authorizationHeader("Bearer 871b743e28e361ebed1dceefa62c3bfe")
    .contentTypeHeader("application/json")

  /**
    *设置请求的方式和路径并命名此请求名称，建议请求名称全局唯一
    */
  object HttpDemo {


    val demo = exec(
      http("workspaceWebHost")
        .post("/WorkspacesWebService/feop/loadRecordData")
        .body(StringBody("{ \"requests\": [ { \"table\": \"block\", \"id\": \"a0452b01-3d2a-4f36-985e-32c832a33d5d\" }, { \"table\": \"block\", \"id\": \"5b97728d-ac52-4680-a6e5-eff234dcf8af\" }, { \"table\": \"block\", \"id\": \"8b4447e5-8fe9-4468-b188-1e073f7e06dc\" } ] }"))
        asJson
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
  val scn = scenario("loadRecord").exec(HttpDemo.demo)

  //设置请求场景
  setUp(
    scn.inject(constantUsersPerSec(200).during(5.seconds)),
  ).protocols(httpConf)
}