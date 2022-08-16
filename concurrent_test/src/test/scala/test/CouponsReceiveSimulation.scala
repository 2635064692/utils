package test

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration.{DurationInt, FiniteDuration}

/**
 * 优惠券领取压测
 */
class CouponsReceiveSimulation extends Simulation {

  /**
   * 设置请求的根路径
   */
  val httpConf = http.
    baseUrl("http://192.168.1.212:32000/MasService")
    .acceptHeader("*/*")
    .contentTypeHeader("application/json")

  /**
    *设置请求的方式和路径并命名此请求名称，建议请求名称全局唯一
    */
  object HttpDemo {
    val demo = exec(
      http("baidu_home").
        post("/activity/receive").
        body(StringBody("{\n  \"activityCode\": \"b0fb327c92660c530fe40505ddb54b50\",\n  \"orgCode\": \"004002e00001-cp20201224172302SEA#-000-000-000-004\",\n  \"eventType\": \"VENDOR_PROVIDE\"\n}")).
        asJson
    )
  }

  /**
  设置请求属于归属方案
   */
  val scn = scenario("BaiduSimulation").exec(HttpDemo.demo)



  setUp(
    scn.inject(rampUsers(1110).during(5.seconds)),
  ).protocols(httpConf)
  //设置请求场景
//  setUp(scn.inject(rampUsers(1).during(10.toSeconds)).protocols(httpConf))
}

//https://testerhome.com/topics/22189

//请求体构建
//方案配置
//请求公共配置
//请求模拟场景设置

//setUp(
//  scn.inject(
//    nothingFor(4 seconds), // 1
//    atOnceUsers(10), // 2
//    rampUsers(10) over(5 seconds), // 3
//    constantUsersPerSec(20) during(15 seconds), // 4
//    constantUsersPerSec(20) during(15 seconds) randomized, // 5
//    rampUsersPerSec(10) to(20) during(10 minutes), // 6
//    rampUsersPerSec(10) to(20) during(10 minutes) randomized, // 7
//    splitUsers(1000) into(rampUsers(10) over(10 seconds)) separatedBy(10 seconds), // 8
//    splitUsers(1000) into(rampUsers(10) over(10 seconds)) separatedBy(atOnceUsers(30)), // 9
//    heavisideUsers(1000) over(20 seconds) // 10
//  ).protocols(httpConf)
//)


//
//1、nothingFor(4 seconds)-
//-  - 在指定的时间段(4 seconds)内什么都不干
//  2、atOnceUsers(10)
//-  - 一次模拟的用户数量(10)。
//3、rampUsers(10) over(5 seconds)
//-  - 在指定的时间段(5 seconds)内逐渐增加用户数到指定的数量(10)。
//4、constantUsersPerSec(10) during(20 seconds)
//-  - 以固定的速度模拟用户，指定每秒模拟的用户数(10)，指定模拟测试时间长度(20 seconds)。
//5、constantUsersPerSec(10) during(20 seconds) randomized
//-  - 以固定的速度模拟用户，指定每秒模拟的用户数(10)，指定模拟时间段(20 seconds)。用户数将在随机被随机模拟（毫秒级别）。
//6、rampUsersPerSec(10) to (20) during(20 seconds)
//-  - 在指定的时间(20 seconds)内，使每秒模拟的用户从数量1(10)逐渐增加到数量2(20)，速度匀速。
//7、rampUsersPerSec(10) to (20) during(20 seconds) randomized
//-  - 在指定的时间(20 seconds)内，使每秒模拟的用户从数量1(10)增加到数量2(20)，速度随机。
//8、splitUsers(10) into(rampUsers(10) over(10 seconds)) separatedBy(10 seconds)-
//-  - 反复执行所定义的模拟步骤(rampUsers(100) over(10 seconds))，每次暂停指定的时间(10 seconds)，直到总数达到指定的数量(10)
//9、splitUsers(100) into(rampUsers(10) over(10 seconds)) separatedBy(atOnceUsers(30))
//-  - 反复依次执行所定义的模拟步骤1(rampUsers(10) over(10 seconds))和模拟步骤2(atOnceUsers(30))，直到总数达到指定的数量(100)左右
//  10、heavisideUsers(100) over(10 seconds)
//-  -  在指定的时间(10 seconds)内使用类似单位阶跃函数的方法逐渐增加模拟并发的用户，直到总数达到指定的数量(100).简单说就是每秒并发用户数递增。
//原文链接：https://blog.csdn.net/qq_37023538/article/details/54950827


//object Demo {
//
//  val demo = exec(
//    http("Post")
//      .post("/computers")  // 发送post请求
//      .queryParam("key","value")  // 增加query参数
//      .queryParamMap(Map("key"->"value"))  // 以Map的形式增加query参数
//      .formParam("key", "value")  // form表单格式发送参数
//      .formParamMap(Map("key"->"value"))  // 以Map的形式增加form表单格式参数
//      .body(StringBody("""{"key":"value"}""")).asJson  // 请求的body内容格式为json
//      .multivaluedFormParam("key","value")  // 发送multivalue格式的from表单
//  )
//}
//val demotwo =  exec(
//  http("get")
//    .httpRequest("get","/")  // 发送get请求根目录
//)

//活跃用户数指的是在测试时间段内，正在进行请求的用户数，会分场景测试展示，这里只有一个场景AddNewComputer，所以两条线重合了。