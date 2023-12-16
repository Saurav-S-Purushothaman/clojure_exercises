;; STATE AND CONCURRENCY

;; ATOMS
;; atoms are desinged to store the state of something that are independent
;; create an atom 
(def who-atom (atom :caterpillar))
@who-atom

;; ways to change the value of atom;; 

;; using reset! 
;; it simply replaces the old value with new value and returns the new value
(reset! who-atom :chrysalis)
@who-atom
;; note the !. Its idiomatic in clojure to use it to indicate that functions changes state

;; using swap! 
;; swap applies a function to the old value of the state and sets to vew value
(defn change [state]
 (case state 
   :caterpillar :chrysalis
   :chrysalis :butterfly
   :butterfly))

(reset! who-atom :caterpillar)
(swap! who-atom change)
(swap! who-atom change)
(swap! who-atom change)

;; NOTE: function used must be free of side-effect.
;; this is how swap works
;; swap! reads the current value of the atom.
;; It applies the provided update function to the current value to calculate a new value.
;; It compares the calculated new value with the current value of the atom to ensure that no other thread has modified the atom in the meantime.
;; If the comparison succeeds (i.e., no other thread has changed the value), swap! sets the atom's value to the calculated new value.
;; If the comparison fails (i.e., another thread has changed the value), swap! retries the entire process.
;; therefore whatever side effect is there in the swap, it will be executed multiple times

;; when dereferencing an atom we wil either see values before applying the funtion 
;; or the result after applying the function.
  
;; lets learn about dotimes first 
;; (dotimes [i 5] (println "Iteraton:" i))
;; it executes the function 5 times and keep the binding to i

(def counter (atom 0))
(dotimes [_ 5] (swap! counter inc))
;; @counter

;; now this is just single thread. 
;; we need to execute this in multiple threads
;; for that we can use the future function.
;; future functions take in a form and execute it another thread

;; (let [n 5]
;;   (future (dotimes [_ n] (swap! counter inc)))
;;   (future (dotimes [_ n] (swap! counter inc)))
;;   (future (dotimes [_ n] (swap! counter inc))))

;; @counter ;; --> returns 20

;; what if the function has side-effect
;; lets create a function with side effect.

(defn inc-print [val]
 (println val)
 (inc val))

(def counter (atom 0))


;; (let [n 1]
;;   (future (dotimes [_ n] (swap! counter inc-print)))
;;   (future (dotimes [_ n] (swap! counter inc-print)))
;;   (future (dotimes [_ n] (swap! counter inc-print))))


;; clojure ref's
;; what if we have more than one thing that need to change in coordinated fashion?
;; refs allow coordinated shared state. 
;; how refs are different from atoms? 
;; you need to change the state of the value within the transaction.
;; accomplished by software transactional memory (STM)
;; although its not clear. Lets slowly get involved with it.

(def alice-height (ref 3))
(def alice-right (ref 10))

;; like atom, refs are reference to state, so dereference is required.
(str "alice height is " @alice-height " and no.of mush in right hand is " @alice-right)

;; we will create a function that increases alice height by 24 inch for each bite she takes
(defn eat-from-right-hand  [] 
  (when (pos? @alice-right)
    (alter alice-right dec)
    (alter alice-height #(+ % 24))))


;; (eat-from-right-hand)
;; Execution error (IllegalStateException) at user/eat-from-right-hand (state-concurrency.clj:94).
;; No transaction running
;; we get this because we need to run this in a transaction.
;; using dosync macro, this will coorindinate any state change within in a transaction.

(dosync (eat-from-right-hand)) ;; returns 27
;; lets modify eat-from-right-hand so that we can include dosync inside the function

;; (def alice-height (ref 3))
;; (def alice-right (ref 10))
;; 
;; (defn eat-from-right-hand2 []
;;  (dosync (when (pos? @alice-right)
;;           (alter alice-right dec)
;;          (alter alice-height #(+ 24 %)))))
;; 
;; (let [n 2]
;;   (future (dotimes [_ n] (eat-from-right-hand2)))
;;   (future (dotimes [_ n] (eat-from-right-hand2)))
;;   (future (dotimes [_ n] (eat-from-right-hand2))))


(def alice-height (atom 3))
(def alice-right (atom 10))

(defn eat-from-right-hand2 []
  (when (pos? @alice-right)
          (swap! alice-right dec)
         (swap! alice-height #(+ 24 %))))

(let [n 2]
  (future (dotimes [_ n] (eat-from-right-hand2)))
  (future (dotimes [_ n] (eat-from-right-hand2)))
  (future (dotimes [_ n] (eat-from-right-hand2))))

;; There is another function called commute that we could use instead of alter. It must be
;; called in a transaction, just like alter, and also takes a ref and a function. The difference
;; between them is that commute will not retry during the transaction. Instead, it will use an
;; in-transaction-value in the meantime, finally setting the ref value at the commit point in
;; the transaction. This feature is very nice for speed and limiting retries. On the other hand,
;; the function that commute applied must be commutative (where the order of the operands
;; does not matter, like addition), or have a last-one-in-wins behavior. We can take a look at
;; our last example with commute instead:


;; ref-set -> use this whenever you have one ref that is defined in relation with other
;; lets demonstrate this 
;; here is x and y set 




(def x (ref 1))
(def y (ref 1))

(defn new-values []
  (dosync 
    (alter x inc)
    (ref-set y (+ 2 @x))))

(let [n 2]
  (future (dotimes [_ n] (new-values)))
  (future (dotimes [_ n] (new-values))))

;; (Thread/sleep 1000) ;; this will wait for all the threads to be finished and process later.
(str "The value of x is " @x " and the value of y is " @y)

;; so we can conclude that refs and ref-set are good when we want to have something like state dependency and program that.

;; so basically atoms - indepentend sychronous change.
;; refs co-ordinated synchronous change
;; what if you don't mind about the state change. 
;; if you have work to be done and you don't need the result righ away then you can use agents
;; then your solution is agent.

;; creating agent
(def who-agent (agent :caterpillar))


;; we change the state of agent using send 
(defn change [state]
  (case state 
  :caterpillar :chrysalis 
  :chrysalis :butterfly
  :butterfly))

(send who-agent change)

;; lets see how this work
;; send dispatches action to an agent which will get processed by a thread-pool
;; interest thing is action dispatched by send are kind of like pipeline. 
;; Agent will process only one action at a time. Each thread will be executed one at a time like a pipeline
;; also they will be processed in the same order in which they were dispatched if they were dispatched in the same order.

;; NOTE: using send might not be enough if you are doing IO bound operations like writing to log file. 
;; reason is it might block IO
;; use send-off in this case. 
;; send uses a fixed thread pool which can block the IO but is good for CPU bound operation.
;; using send-off is good for IO bound operation because it uses expandable thread-pool
(send-off who-agent change)
;; okay, now what if the agent has error?

(def who-agent (agent :caterpillar))

(defn change [state] 
  (case state
    :caterpillar :chrysalis
    :chrysalis :butterfly
    :butterfly))

(defn change-error [state]
  (throw (Exception. "Boom!")))

(send who-agent change-error)
;; so now we have purposefully throwed an exception to the agent.
;; so agent failed. state of the agent is still caterpillar but. 
;; this exception will be cached, and when the state of the agent is actually changed, 
;; it will throw this error from the cache (pretty cool)

;; lets try to actually change the state of the agent. 

;; (send who-agent change)
;; Execution error at user/change-error (state-concurrency.clj:210).
;; Boom!
;; We got this as the error message

;; agent error can also be detected using agent-error 
;; (agent-error who-agent)

;; the agent will continue to be in this state and we can't change the value of the agent 
;; since it has thrown an error at a point of time. 
;; if we want to change this behavior of agent after it gets thrown an error you have 
;; to use the restart-agent  command.

(restart-agent who-agent :caterpillar)
;; you have to restart the agent as soon as it gets thrown the error.
;; otherwise it won't work. (strange)

(send-off who-agent change)
@who-agent

;; okay there are different stratergies to handle error programmatically
;; first is to set the error 
;; we can use set-error-mode! to specify the mode in which agent should work.
;; set-error-mode! can be set to :continue and :fail
;; if its set to continue, we can add set-error-handler! function to handle the error.
;; no need to restart the agent.
;; set-error-handler takes two args, the agent and the error message

(def who-agent (agent :caterpillar))

(set-error-mode! who-agent :continue)

(defn error-handler-fn [a ex]
  (println "Error " ex "The value is " @a))

(set-error-handler! who-agent error-handler-fn)

(send who-agent change-error)
;; with these pre setup ready, lets try to throw error to the agent one more time 

;; what are the actual use of agent.





