(defproject serpant-talk "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [camel-snake-kebab "0.4.3"]]
  ;; coordinate format is common thing in maven project.
  ;; [group-id/artifact-id "version"] --
  ;; here group-id = org.clojure
  ;; artifact-id = clojure
  ;; version = 1.11.1
  :repl-options {:init-ns serpant-talk.core})
