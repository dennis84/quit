package quit.android

import com.loopj.android.http._
import org.apache.http.Header
import com.github.nscala_time.time.Imports._
import upickle._
import scala.concurrent._

class Client(id: String) {
  val API_URL = "https://arcane-beach-3744.herokuapp.com"
  val client = new AsyncHttpClient

  def list: Future[List[DateTime]] = {
    val p = promise[List[DateTime]]
    client.get(API_URL + "/" + id, handle(p))
    p.future
  }

  def put: Future[List[DateTime]] = {
    val p = promise[List[DateTime]]
    client.post(API_URL + "/" + id, handle(p))
    p.future
  }

  def del: Future[List[DateTime]] = {
    val p = promise[List[DateTime]]
    client.delete(API_URL + "/" + id, handle(p))
    p.future
  }

  private def handle(p: Promise[List[DateTime]]) = new AsyncHttpResponseHandler {
    def onSuccess(
      statusCode: Int,
      headers: Array[Header],
      responseBody: Array[Byte]
    ) = {
      val list = read[List[String]](new String(responseBody))
      val dates = list map DateTime.parse
      p success dates
    }

    def onFailure(
      statusCode: Int,
      headers: Array[Header],
      responseBody: Array[Byte],
      error: Throwable
    ) = p failure error
  }
}
