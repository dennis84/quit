package quit.android

import com.loopj.android.http._
import org.apache.http.Header
import org.joda.time.DateTime
import scala.concurrent._
import upickle._

class Client(id: String, url: String) {
  val client = new AsyncHttpClient

  def list: Future[List[DateTime]] = {
    val p = promise[List[DateTime]]
    client.get(url + "/" + id, handle(p))
    p.future
  }

  def put: Future[List[DateTime]] = {
    val p = promise[List[DateTime]]
    client.post(url + "/" + id, handle(p))
    p.future
  }

  def del: Future[List[DateTime]] = {
    val p = promise[List[DateTime]]
    client.delete(url + "/" + id, handle(p))
    p.future
  }

  private def handle(p: Promise[List[DateTime]]) = new AsyncHttpResponseHandler {
    def onSuccess(
      statusCode: Int,
      headers: Array[Header],
      responseBody: Array[Byte]
    ) = {
      val list = read[List[String]](new String(responseBody))
      p success (list map DateTime.parse)
    }

    def onFailure(
      statusCode: Int,
      headers: Array[Header],
      responseBody: Array[Byte],
      error: Throwable
    ) = p failure error
  }
}
