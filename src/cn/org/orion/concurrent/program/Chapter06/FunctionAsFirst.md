# Function as first
***
## If you use JQuery,You would use next code usually
```
$("button").click(function() {
 $("li").each(function() {
   alert($(this).test())
 });
});
```
***
Attention here's parameter in each() method,this is an Anonymous function,while iterating all li nodes,it will alert li node's text context.Let function as a parameter to pass on another function,this is functional program/scheme's one typical performance.

***
## Then look another case
```
function f1(){
	var n=1;
	function f2(){
	 alert(n);
	}
	return f2;
}
var result=f1();
result();  // 1
```
***
This is also a JavaScript code.in this block,attention function f1's return value,it call back function f2.Count backwards two line,it will return f2 function and give its value to result,actually,this result was a function,then direct to f2.So call result then will print value n.
Function could be another function's return value,also is functional program/scheme's important Characteristic.
