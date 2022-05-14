Ext.ns('App');
//Ext.Loader.setConfig({ enabled : true, disableCaching : false });
//Ext.Loader.setPath('Sch', '../../js/Sch');
Ext.Loader.setConfig({
    enabled: true
});
Ext.Loader.setPath('Ext.ux', '../extjs/ux');
Ext.require([
    'Ext.ux.form.SearchField'
    ]);
var modificacion = 'no';
//LAG
var histOTselec = '';
var idOt = '';
var winOT,listTareasOtNew,disabledDetalleCol=false;
Ext.onReady(function () {
	
});
App.SchedulerDemo = {
    // Initialize variables
    iniciandoVariables: function () {},
    // Initialize application
    init: function () {},
    loadResourceStore: function () {},
    loadEventStore: function () {},
    reloadEventStore: function () {},
    verificarCambios: function () {},
    asignar: function () {},
    cambioFlujo: function (flujoActual) {},
    crearDetalleEmpleado: function () {},
    cambiaTituloTipoEmpleado: function () {},
    hideShowTableroCita: function (opcion) {},
    cambiaTimeResolution: function () {},
    config: function () {},
    reset: function () {},
    CerrarVentana: function (nombre) {
        (Ext.getCmp(nombre)).close();
    },
    mensajeSistema: function (titulo,mensaje) 
    {
        if(mensaje!='')
        {
            Ext.Msg.alert(titulo, mensaje, function(btn, text){
                if (btn == 'ok'){
                    location.reload(); 
                }
            });
        }
    },
    jsRemoveWindowLoad: function ()
    {
    	// eliminamos el div que bloquea pantalla
    	$("#WindowLoad").remove();
    },
    jsShowWindowLoad:  function (mensaje)
    {
    	App.SchedulerDemo.jsRemoveWindowLoad();
    	//si no enviamos mensaje se pondra este por defecto
        if (mensaje === undefined) mensaje = "Procesando la información&amp;lt;br&amp;gt;Espere por favor";
     
        //centrar imagen gif
        height = 20;//El div del titulo, para que se vea mas arriba (H)
        var ancho = 0;
        var alto = 0;
     
        //obtenemos el ancho y alto de la ventana de nuestro navegador, compatible con todos los navegadores
        if (window.innerWidth == undefined) ancho = window.screen.width;
        else ancho = window.innerWidth;
        if (window.innerHeight == undefined) alto = window.screen.height;
        else alto = window.innerHeight;
     
        //operación necesaria para centrar el div que muestra el mensaje
        var heightdivsito = alto/2 - parseInt(height)/2;//Se utiliza en el margen superior, para centrar
     
       //imagen que aparece mientras nuestro div es mostrado y da apariencia de cargando
        imgCentro = "<div style='text-align:center;height:" + alto + "px;'><div  style='color:#000;margin-top:" + heightdivsito + "px; font-size:20px;font-weight:bold'>" + mensaje + "</div><img  src='http://hdeleon.net/tutoriales/img/load.gif'></div>";
     
            //creamos el div que bloquea grande------------------------------------------
            div = document.createElement("div");
            div.id = "WindowLoad"
            div.style.width = ancho + "px";
            div.style.height = alto + "px";
            $("body").append(div);
     
            //creamos un input text para que el foco se plasme en este y el usuario no pueda escribir en nada de atras
            input = document.createElement("input");
            input.id = "focusInput";
            input.type = "text"
     
            //asignamos el div que bloquea
            $("#WindowLoad").append(input);
     
            //asignamos el foco y ocultamos el input text
            $("#focusInput").focus();
            $("#focusInput").hide();
     
            //centramos el div del texto
            $("#WindowLoad").html(imgCentro);
    }
};