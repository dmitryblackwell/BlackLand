<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form"  	uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
  <title>Javascript Platformer</title>
  <style>
    body            { background: #111; }
    #canvas         { cursor: crosshair; }
    #instructions b { color: #DDD; }
  </style>

</head>

<body>

    <canvas id="canvas" width="2048" height="1536">
    </canvas>



  <script src="<c:url value="/resources/libs/jquery.min.js"/>"></script>
  <script src="<c:url value="/resources/libs/fpsmeter.min.js"/>"></script>

  <script src="<c:url value="/resources/scripts/utils.js"/>"></script>
  <script src="<c:url value="/resources/scripts/initialize.js"/>"></script>
  <script src="<c:url value="/resources/scripts/ajax.js"/>"></script>
  <script src="<c:url value="/resources/scripts/loop_update.js"/>"></script>
  <script src="<c:url value="/resources/scripts/rendering.js"/>"></script>
  <script src="<c:url value="/resources/scripts/game_loop.js"/>"></script>

</body>
</html>
