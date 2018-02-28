<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
  <meta name="author" content="Yigit Yigit Ce.[pulyavserdce.com]" />
  <meta name="description" content="Site description" />
  <meta name="keywords" content="keywords, keyword, seo, google" /> 
  <meta name="apple-mobile-web-app-status-bar-style" content="black" /> 
  <meta name="apple-mobile-web-app-capable" content="yes" /> 
  <title>Error</title>
   <link rel="stylesheet" media="screen" href="<%= request. getContextPath()%>/css/style7.css" type="text/css" />
   <link rel="shortcut icon" type="image/x-icon" href="favicon.png" />
   <link rel="icon" type="image/x-icon" href="favicon.png" />
   <link rel="apple-touch-icon" href="favicon.png" />
   <link rel="apple-touch-startup-image" href="favicon.png" />
</head>
<body>
<div class="controller">
    <div class="objects"> 
    <!-- text area -->
    <div class="text-area rotate">
    <p class="error">Error </p>
    <p class="details">有一个问题<br /><br />您要访问的页面去喝咖啡了！稍等再访问哦~</p> 
    </div> 
    <!-- text area -->
    <!-- home page --> 
    <div class="homepage rotate">
    <a href="<%= request. getContextPath()%>/toLogin.do">重新登录</a> 
    </div> <!-- home page --> 
    </div> <!-- social-icons --> 
    <div class="social">
    
    </div> 
    <!-- social-icons --> 
    </div> 

</body>
</html>