//# sourceMappingURL=bespoke-scale.js.map
!function(k){if("object"==typeof exports&&"undefined"!=typeof module)module.exports=k();else if("function"==typeof define&&define.amd)define([],k);else{var a;"undefined"!=typeof window?a=window:"undefined"!=typeof global?a=global:"undefined"!=typeof self&&(a=self);a=a.bespoke||(a.bespoke={});a=a.plugins||(a.plugins={});a.scale=k()}}(function(){return function a(e,g,l){function c(d,b){if(!g[d]){if(!e[d]){var f="function"==typeof require&&require;if(!b&&f)return f(d,!0);if(h)return h(d,!0);throw Error("Cannot find module '"+
d+"'");}f=g[d]={exports:{}};e[d][0].call(f.exports,function(a){var b=e[d][1][a];return c(b?b:a)},f,f.exports,a,e,g,l)}return g[d].exports}for(var h="function"==typeof require&&require,b=0;b<l.length;b++)c(l[b]);return c}({1:[function(a,e,g){e.exports=function(a){return function(c){var h=c.parent,b=c.slides[0],d=b.offsetHeight,e=b.offsetWidth,b="zoom"===a||"zoom"in h.style&&"transform"!==a,f=function(a){var b=document.createElement("div");b.className="bespoke-scale-parent";a.parentNode.insertBefore(b,
a);b.appendChild(a);return b},g=b?c.slides:c.slides.map(f),m=function(a){return["Moz","Webkit","O","ms"].reduce(function(b,c){return c+a in h.style?c+a:b},a.toLowerCase())}("Transform"),n=b?function(a,b){b.style.zoom=a}:function(a,b){b.style[m]="scale("+a+")"};c=function(){g.forEach(n.bind(null,Math.min(h.offsetWidth/e,h.offsetHeight/d)))};window.addEventListener("resize",c);c()}}},{}]},{},[1])(1)});