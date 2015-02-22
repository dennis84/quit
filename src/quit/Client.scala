package quit.android

import com.loopj.android.http._
import org.apache.http.Header
import com.github.nscala_time.time.Imports._
import upickle._

class Client(id: String) {
  val client = new AsyncHttpClient

  def list(fn: List[DateTime] => Unit) =
    client.get("http://10.0.2.2:9000/" + id, handle(fn))

  def put(fn: List[DateTime] => Unit) =
    client.post("http://10.0.2.2:9000/" + id, handle(fn))

  def del(fn: List[DateTime] => Unit) =
    client.delete("http://10.0.2.2:9000/" + id, handle(fn))

  private def handle(fn: List[DateTime] => Unit) = new AsyncHttpResponseHandler {
    def onSuccess(
      statusCode: Int,
      headers: Array[Header],
      responseBody: Array[Byte]
    ) = fn(read[List[String]](new String(responseBody)) map DateTime.parse)

    def onFailure(
      statusCode: Int,
      headers: Array[Header],
      responseBody: Array[Byte],
      error: Throwable
    ) = android.util.Log.e("API-ERROR", "")
  }
}
