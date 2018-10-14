<%@ page contentType="text/html;charset=UTF-8"%>
<html>
  <head>
    <title>$Title$</title>
    <script type="text/javascript" src="script/jquery-3.3.1.js"></script>
    <script>
      var blocks;
        $.ajax({
            url: "getmap",
            type: "GET",
            cashed: false,
            success: function (response) {
                blocks = JSON.parse(response);
            }
        });

    </script>

    <script type="text/javascript" src="script/keypush.js"></script>
    <script type="text/javascript" src="script/onload.js"></script>

  </head>
  <body>
  <canvas id="canvas" width="900" height="800" style="margin: auto;"></canvas>


  </body>
</html>
