<html>
<#--我是注释-->
value: ${value}  <#--解析并显示出value的值 -->
value2: $!{value2} (value2 不存在 直接显示文本)<#-- ${value2} value2不存在的话会报错 -->

<br>

list遍历：
<#--#foreach($color in $colors)-->
<#list directions as direction>  <#-- 跟之前的写法是反的 把directions里面的每一个direction列出来 前面是多的 后面是少的-->
    ${direction}
</#list>

<br>

<#-- map.keySet()-->
<#-- ${map[key]}  map.get(key) -->
<#list map?keys as key>
    key: ${key} value: ${map[key]}
</#list>

<br>

<#list map?values as value>
    value: ${value}
</#list>

<br>

<#-- 用[]代替. 要访问的属性写在[]内的双引号内 或user.getName()-->
User: ${user["name"]}  ${user.getName()}
Age: ${user["age"]}
调用User的sayHi(): ${user.sayHi("Tom")}

<br>

<#--声明变量-->
<#assign title="nowcoder">
title: ${title}

<#--会进行解析-->
<#include "header.ftl">

<#--<#import "header.ftl">-->

<#--<#macro print directions>-->
    <#--Direction: ${direction}-->
<#--</#macro>-->


</html>
