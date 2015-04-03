package quit.app.tweaks

trait ListTweaks {

  implicit class GroupWhile[A](xs: List[A]) {
    def groupWhile(f: (A, A) => Boolean): List[List[A]] = xs match {
      case Nil => Nil
      case _ => xs.foldLeft(List(List.empty[A]))((a, x) => a.head match {
        case Nil => List(x) :: a.tail
        case h if(!f(h.last,x)) => List(x) :: a
        case h => (h ::: List(x)) :: a.tail
      }).reverse
    }
  }
}
