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
    #platformer     { margin: 1em auto; position: relative; width: 512px; height: 384px; }
    #canvas         { display: inline-block; width: 100%; height: 100%; }
    #instructions   { font-style: italic; color: #999; font-size: 9pt; margin-left: 1em; margin-top: 0.5em; }
    #instructions b { color: #DDD; }
    @media screen and (min-width:     0px) and (min-height:    0px) { #platformer { width:  512px; height: 384px; font-size:  7pt; } } /* block = 16 */
    @media screen and (min-width:   840px) and (min-height:  530px) { #platformer { width:  640px; height: 480px; font-size:  9pt; } } /* block = 20 */
    @media screen and (min-width:   968px) and (min-height:  626px) { #platformer { width:  768px; height: 576px; font-size: 11pt; } } /* block = 24 */
    @media screen and (min-width:  1096px) and (min-height:  722px) { #platformer { width:  896px; height: 672px; font-size: 13pt; } } /* block = 28 */
    @media screen and (min-width:  1224px) and (min-height:  818px) { #platformer { width: 1024px; height: 768px; font-size: 15pt; } } /* block = 32 */
  </style>

</head>

<body>

  <div id="platformer">

    <canvas id="canvas">
      <div class="unsupported">
        Sorry, this example cannot be run because your browser does not support the &lt;canvas&gt; element
      </div>
    </canvas>

    <div id="instructions">
      <b>LEFT/RIGHT</b> to move, <b>SPACE</b> to jump, collect gold and jump on monsters.
    </div>

  </div>


  <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
  <script src="<c:url value="/resources/scripts/fpsmeter.min.js"/>"></script>
  <script src="<c:url value="/resources/scripts/platformer.js"/>"></script>

</body>
</html>
