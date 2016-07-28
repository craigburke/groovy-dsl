//= require /bespoke/bespoke
//= require_full_tree /bespoke/plugins/
//= require highlightjs/highlight.pack.min

(function() {
    var BULLETS_SELECTOR = '.build,.build-items>*:not(.build-items)';

    bespoke.from("article.deck", [
        bespoke.plugins.classes(),
        bespoke.plugins.keys(),
        bespoke.plugins.bullets(BULLETS_SELECTOR),
        bespoke.plugins.scale('transform'),
        bespoke.plugins.state(),
        bespoke.plugins.hash(),
        bespoke.plugins.overview({margin: 300, title: true, numbers: true}),
        bespoke.plugins.touch(),
        bespoke.plugins.backdrop(),
        bespoke.plugins.progress(),
        bespoke.plugins.fullscreen()
    ]);

    hljs.initHighlightingOnLoad();

})();