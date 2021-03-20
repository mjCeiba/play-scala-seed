package controllers

import play.api.mvc._
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class AsyncController @Inject()(
                                 val myExecutionContext: ExecutionContext,
                                 val controllerComponents: ControllerComponents)
  extends BaseController {

  def futureRequest = Action.async {
    Future {
      Ok("result of blocking call")
    }(myExecutionContext)
  }
}