//自定义消息框
function Message(){
	var layer=document.createElement("div");
	layer.id="layer";
    var style=
    {
    		
        background:"gainsboro",
        position:"absolute",
        zIndex:10,
        width:"300px",
        height:"100px",
        right:"20px",
        bottom:"20px",
        paddingLeft:"40px",
        paddingTop:"20px",
        lineHeight:"25px",
        border:"1px solid gray"
    }
    for(var i in style)
        layer.style[i]=style[i];   
    if(document.getElementById("layer")==null)
    {
        document.body.appendChild(layer);
        setTimeout("document.body.removeChild(layer)",3000)
    }
}