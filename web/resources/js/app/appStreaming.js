/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var canvas = document.getElementById("preview");
var context = canvas.getContext("2d");

canvas.width = 800;
canvas.height = 600;

context.width = canvas.width;
context.height = canvas.height;

var video = document.getElementById("video");
var socket = io();

function logger(msg)
{
    $("#logger").text(msg);
}

function loadCam(stream)
{
    video.src = window.URL.createObjectURL(stream);
    logger('Camara cargada correctamente [ok]');
}

function loadFail()
{
    logger('Camara no conectada, por favor revise camara');
}

function viewVideo(video,context)
{
    context.drawImage(video,0,0,context.width,context.height);
}

$(function(){
    navigator.getUserMedia = (navigator.getUserMedia || navigator.
            webkitGetUSerMedia || navigator.mozGetUserMedia || navigator.
            msgGetUserMedia);
    if(navigator.getUserMedia)
    {
        navigator.getUserMedia({video : true},loadCam, loadFail);
    }
});