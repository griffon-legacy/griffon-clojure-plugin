/*
 * Copyright 2009-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the 'License');
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an 'AS IS' BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * @author Andres Almiray
 */
class ClojureGriffonPlugin {
    // the plugin version
    String version = '0.9'
    // the version or versions of Griffon the plugin is designed for
    String griffonVersion = '0.9.5 > *'
    // the other plugins this plugin depends on
    Map dependsOn = ['lang-bridge': '0.5']
    // resources that are included in plugin packaging
    List pluginIncludes = []
    // the plugin license
    String license = 'Apache Software License 2.0'
    // Toolkit compatibility. No value means compatible with all
    // Valid values are: swing, javafx, swt, pivot, gtk
    List toolkits = []
    // Platform compatibility. No value means compatible with all
    // Valid values are:
    // linux, linux64, windows, windows64, macosx, macosx64, solaris
    List platforms = []
    // URL where documentation can be found
    String documentation = ''
    // URL where source can be found
    String source = 'https://github.com/griffon/griffon-clojure-plugin'

    List authors = [
        [
            name: 'Andres Almiray',
            email: 'aalmiray@yahoo.com'
        ]
    ]
    String title = 'Brings the Clojure language compiler and libraries'
    // accepts Markdown syntax. See http://daringfireball.net/projects/markdown/ for details
    String description = '''
The Clojure plugin enables compiling and running Clojure code on your Griffon application.
This plugin is heavily influenced by its [Grails counterpart][1], even borrows some of its code.

Usage
-----

You must place all Clojure code under `$appdir/src/clojure`, it will be compiled first before any
sources available in `griffon-app` or `src/main which means you can't reference any of those sources
from your Clojure code, while the Groovy sources can. You will be able to use the [LangBridge Plugin][2]
facilities to communicate with other JVM languages.

Sources placed under `$appdir/src/clojure` will generate Java classes using macros provided by Clojure,
for example `$appdir/src/clojure/griffon/clojure/Greet.clj`

        (ns griffon.clojure.Greet
            (:gen-class
             :methods [[greet [String] String]]))
        (defn -greet
          ([this greetee]
             (str "Hello " greetee "!")))
        (defn -main
          [greetee]
          (println (.greet (griffon.clojure.Greet.) greetee)))

This will generate a Java class named `griffon.clojure.Greet`, which can be used inside any Griffon artifact,
for example a Controller

        import griffon.clojure.Greet
        class MyController {
           def action = { evt = null ->
              def greeter = new Greet()
              def output = greeter.greet('Griffon')
              // do something with output
           }
        }

Starting from version 0.2 you will be able to load clojure scripts at any time. By default all scripts placed
at `$basedir/griffon-app/resources/clj` will be loaded when the application bootstraps itself. For example 
`griffon-app/resources/clj/demo.clj` might look like this:

        (ns griffon)
        (defn add_numbers [a b] (+ a b))

With that code in place, the add_numbers function may be executed as a method call on a dynamic property named
`clj` from a Griffon controller. See below:

        class FooController {
            def addNumbers = { evt = null ->
                // invoke the function as a method on the
                // clj dynamic property...
                model.z = clj.add_numbers(model.x, model.y)
            }
        }

The dynamic property will be named `clj` by default. The name of the property may be set explicitly in 
`griffon-app/conf/Config.groovy` by assigning a value to the `griffon.clojure.dynamicPropertyName` property.

        griffon.clojure.dynamicPropertyName = 'clojurePropertyName'

For most applications, the default name of `clj` should be fine. You can also alter in which artifacts the
property gets injected, by default only controllers will have that property. See `griffon-app/conf/Config.groovy`
and look for the following entry

        griffon.clojure.injectInto = ["controller"]

Finally, you can load any Clojure script by calling `cljLoad(String path)` where path will be resolved using
Spring's PathMatchingResourcePatternResolver. The default path used during bootstrap is `"classpath*:/clj/*/.clj"`.
It is also worth mentioning that this method will be injected to all artifacts controlled by `griffon.clojure.injectInto`
and that the prefix `clj` will be affected by `griffon.clojure.dynamicPropertyName`.

Scripts
-------

 * **create-clojure-script** - creates a new Clojure script in `$basedir/griffon-app/resources/clj`.
 * **create-clojure-class** - creates a new Clojure (macro based) class in `$basedir/src/clojure`.
 * **clojure-repl** - executes a Clojure REPL with the application's classpath fully configured.

[1]: http://grails.org/plugin/clojure
[2]: /plugin/lang-bridge
'''
}
