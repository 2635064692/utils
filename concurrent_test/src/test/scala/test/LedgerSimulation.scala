package test

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._
/**
 * 优惠券领取压测
 */
class LedgerSimulation extends Simulation {

  private var url = "http://localhost:9796/SubAccountInnerService";
  /**
    *设置请求的方式和路径并命名此请求名称，建议请求名称全局唯一
    */
//  object HttpDemo {
//    val demo = exec(
//      http("baidu_home").
//        post("/cmobile/midP/test").
//        body(StringBody("")).
//        asJson
//    )
//  }


  object Ledger {
    //feed有4种迭代取值方式：
    // 1、queue（顺序取值，直到取完，所以要注意并发虚拟用户数与参数值一致，否则报异常），缺点：不适合持续迭代，只适合一次性并发场景
    // 2、random（随机取值，会重复取值），缺点：取值存在重复，不适合唯一性取值的场景
    // 3、shuffle 网上资料有这个参数，但是在脚本编写时，无法引用，表示不知如何引用到
    // 4、circular（参数一旦用完，从头开始）
    val feeder = csv("E:\\githubProject\\concurrent_test\\src\\test\\resources\\data\\Test_data.csv").queue
    val Content_type = Map("Content-Type" -> "application/json","Authorization"->"bearer 587f53fd-edf0-421c-9707-913ce50b959c") //定义header
    val process = feed(feeder)
      .exec(http("test_request")
        .get(url + "/inner/ledgerRule/test?input={\"paySn\":\"${paySn}\",\"orderSn\":\"${orderSn}\"}")
        .headers(Content_type)
//        .formParam("input", "ssss")         //post请求填充数据
        .asFormUrlEncoded
        .check(status.is(200))
//        .check(jsonPath("$..token").exists) //判断json中是否存在key
//        .check(jsonPath("$..token").saveAs("Get_token")) // 方法1：获取token值，并将值存入session中Get_token参数
//        //.check(regex("\"token\":\"(.*)\"").saveAs("Get_token"))  // 方法2：获取token值，并将值存入session中Get_token参数
//        .check(jsonPath("$..*").saveAs("Get_body")) //方法1：获取response中body所有值，并存入session中Get_body参数
//        // .check(bodyString.saveAs("Get_body"))  //方法2：获取response中body所有值，此方法获取的结果与jsonPath("$..*")类似
//        .check(header("Date").saveAs("Get_header")) //获取response的header中某个参数值
      )
//      .pause(2) //设置思考时间3s
//      //修改session中信息
//      .exec(session => {
//        val token_value = session("Get_token").as[String] //获取session中Get_token参数值
//        session.set("Get_tokens", token_value) //对session中添加一个新参数，并给新参数赋值
//      }
//      )
      //打印session信息
//      .exec { session =>
//        println(session)
//        session
//      }
  }

  /*******设置场景******/
  //设置场景1
  val scn = scenario("wo玩的就是任性...")     //设置场景名称，可随意定义
    .exec(Ledger.process)  //调用用例
  //设置场景2
//  val scn1 = scenario( "wo扯的都是淡...")
//    .exec(Login.login)

  /*******执行场景策略******/
  /* setUp(
       //执行多个场景
      //scn.inject(atOnceUsers(1)),
      //scn1.inject(rampUsers(2) over(1))
    )*/
  setUp(
    //执行单场景
    scn.inject(constantUsersPerSec(300) during(3))   //每秒运行2个虚拟用户，持续运行10s
  )
}
