package controllers

import akka.util.ByteString
import javax.inject._
import play.api.http.HttpEntity
import play.api.mvc._
import scala.concurrent.{ExecutionContext}


@Singleton
class HomeController @Inject()(
                                val controllerComponents: ControllerComponents,
                                val myExecutionContext: ExecutionContext
                              ) extends BaseController {

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def sayHelloWorld() = Action { implicit request =>
    Ok(s"Hello World -------  GOT REQUEST [${request}]  --------")
  }

  def returnAsResult = Action {
    Result(
      header = ResponseHeader(200, Map.empty),
      body = HttpEntity.Strict(ByteString("Hello world!"), Some("text/plain"))
    )
  }

  def notFound = Action {
    NotFound("Sorry site not found")
  }

  def show(id: Long) = Action { implicit request =>
    Ok(s"The id is ${id}")
  }

  def returnId(id: Long) = Action { implicit request =>
    Ok(s"The id is: ${id}")
  }

  def returnWholeBody() = Action { implicit request =>
    Ok(s"${request.body.asJson.getOrElse(None)}")
  }

  def returnHTML() = Action { implicit request =>
    Ok(<h1>Hello World!</h1>).as(HTML)
  }

  def writerSessionValue() = Action { implicit request =>
    Ok("Welcome user@gmail.com")
      .withSession("connected" -> "user@gmail.com")
  }

  def readerSessionValue() = Action { implicit request =>
    request.session
      .get("connected")
      .map { user =>
        Ok("Hello " + user)
      }
      .getOrElse {
        Unauthorized("Oops, you are not connected")
      }
  }

  def discardSessionValue() = Action { implicit request =>
    Ok("Bye")
      .withNewSession
  }

}
