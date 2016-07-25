<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<%@page import="java.sql.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title> <%= request.getParameter("search")%> - Search</title>
 <link href="css/bootstrap2.css" rel="stylesheet">
<link href='http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,600,200italic,600italic&subset=latin,vietnamese' rel='stylesheet' type='text/css'>
 <link rel="icon" href="http://cliparts.co/cliparts/8cA/Enp/8cAEnpM6i.png">

<style>
        .bubbleChart {
            width: 100%;
            height: 675px;
            margin: 0 auto;
        }
        .bubbleChart svg{
	   background: url(../img/bg.jpg) no-repeat center center scroll;
	  background-color: rgba(0,0,0,0.4);
        }
    </style>
</head>
<body>

    <script src="http://phuonghuynh.github.io/js/bower_components/jquery/dist/jquery.min.js"></script>
    <script src="http://phuonghuynh.github.io/js/bower_components/d3/d3.min.js"></script>
    <script src="http://phuonghuynh.github.io/js/bower_components/d3-transform/src/d3-transform.js"></script>
    <script src="http://phuonghuynh.github.io/js/bower_components/cafej/src/extarray.js"></script>
    <script src="http://phuonghuynh.github.io/js/bower_components/cafej/src/misc.js"></script>
    <script src="http://phuonghuynh.github.io/js/bower_components/cafej/src/micro-observer.js"></script>
    <script src="http://phuonghuynh.github.io/js/bower_components/microplugin/src/microplugin.js"></script>
    <script src="http://phuonghuynh.github.io/js/bower_components/bubble-chart/src/bubble-chart.js"></script>
    <script src="bower_components/bubble-chart/src/plugins/central-click/central-click.js"></script>
    <script src="http://phuonghuynh.github.io/js/bower_components/bubble-chart/src/plugins/lines/lines.js"></script>
     <script type="text/javascript">
    $(document).ready(function () {
  var bubbleChart = new d3.svg.BubbleChart({
    supportResponsive: true,
    //container: => use @default
    size: 600,
    //viewBoxSize: => use @default
    innerRadius: 600 / 3.5,
    //outerRadius: => use @default
    radiusMin: 50,
    //radiusMax: use @default
    //intersectDelta: use @default
    //intersectInc: use @default
    //circleColor: use @default
    data: {
      items: [
        {text: "Java", count: "236"},
        {text: ".Net", count: "382"},
        {text: "Php", count: "170"},
        {text: "Ruby", count: "123"},
        {text: "D", count: "12"},
        {text: "Python", count: "170"},
        {text: "C/C++", count: "382"},
        {text: "Pascal", count: "10"},
        {text: "Something", count: "170"},
      ],
      eval: function (item) {return item.count;},
      classed: function (item) {return item.text.split(" ").join("");}
    },
    plugins: [
      {
        name: "central-click",
        options: {
          text: "(See more detail)",
          style: {
            "font-size": "12px",
            "font-style": "italic",
            "font-family": "Source Sans Pro, sans-serif",
            //"font-weight": "700",
            "text-anchor": "middle",
            "fill": "white"
          },
          attr: {dy: "65px"},
          centralClick: function() {
          }
        }
      },
      {
        name: "lines",
        options: {
          format: [
            {// Line #0
              textField: "count",
              classed: {count: true},
              style: {
                "font-size": "28px",
                "font-family": "Source Sans Pro, sans-serif",
                "text-anchor": "middle",
                fill: "white"
              },
              attr: {
                dy: "0px",
                x: function (d) {return d.cx;},
                y: function (d) {return d.cy;}
              }
            },
            {// Line #1
              textField: "text",
              classed: {text: true},
              style: {
                "font-size": "14px",
                "font-family": "Source Sans Pro, sans-serif",
                "text-anchor": "middle",
                fill: "white"
              },
              attr: {
                dy: "20px",
                x: function (d) {return d.cx;},
                y: function (d) {return d.cy;}
              }
            }
          ],
          centralFormat: [
            {// Line #0
              style: {"font-size": "50px"},
              attr: {}
            },
            {// Line #1
              style: {"font-size": "30px"},
              attr: {dy: "40px"}
            }
          ]
        }
      }]
  });
});
</script>
    
 <div class="bubbleChart"> </div>

</body>
</html>