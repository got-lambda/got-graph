goog.addDependency("base.js", ['goog'], []);
goog.addDependency("../cljs/core.js", ['cljs.core'], ['goog.string', 'goog.array', 'goog.object', 'goog.string.StringBuffer']);
goog.addDependency("../clojure/walk.js", ['clojure.walk'], ['cljs.core']);
goog.addDependency("../got_graph/transform.js", ['got_graph.transform'], ['cljs.core', 'clojure.walk']);
goog.addDependency("../got_graph/core.js", ['got_graph.core'], ['cljs.core']);