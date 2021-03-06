### User customizations

# Put user-specific changes in your own Makefile.user
#  (such as changes to PLUME_DIR or JAVAC or JAVAC_EXTRA_ARGS).
# Make will silently continue if file does not exist.
-include Makefile.user

TOOLS_JAR := ${JAVA_HOME}/lib/tools.jar
# On MacOS, there is no separate tools.jar
ifneq (,$(findstring darwin,$(OSTYPE)))
  TOOLS_JAR := /System/Library/Frameworks/JavaVM.framework/Versions/1.5.0/Classes/classes.jar
endif

# This wildcard doesn't seem to work, so list the jar files individually.
# CLASSPATH:=lib/*:$(JAVA_HOME)/lib/tools.jar
CLASSPATH := ${PLUME_CLASSPATH_PREFIX}:.:src:..:lib/bcel.jar:lib/checkers-quals.jar:lib/commons-logging-1.1.1.jar:lib/commons-io-1.4.jar:lib/commons-lang-2.4.jar:lib/ical4j.jar:lib/ini4j-0.5.1.jar:lib/junit.jar:lib/svnkit.jar:lib/tagsoup-1.2.jar:lib/xom-1.2.1.jar:${TOOLS_JAR}

# On cygwin, translate the classpath and sourcepath to windows format.
# Only do so if this has not already been done, which is determined by
# looking for semicolons (;) in the classpath.
ifeq (cygwin,$(OSTYPE))
  ifneq (;,$(findstring ;,$(CLASSPATH)))
    CLASSPATH := $(shell cygpath -pw "$(CLASSPATH)")
  endif
  TOOLS_JAR := '$(shell cygpath -w $(TOOLS_JAR))'
endif

# hack to add a space
nullstring :=
space := $(nullstring) # a space in the end

ifndef HTML_TO_TEXT
ifneq (,$(shell which lynx 2>/dev/null))
  # Note no trailing space!
  HTML_TO_TEXT = lynx -dump file://$(shell pwd)/
else ifneq (,$(shell which html2text 2>/dev/null))
  # Note trailing space!
  HTML_TO_TEXT = html2text -rcfile html2textrc$(space)
else
  # Note trailing space!
  HTML_TO_TEXT = cat$(space)
endif
endif

export CLASSPATH

JAVAC ?= javac
JAVAC_TARGET ?= -target 5 -source 5
JAVAC_ARGS ?= -g -Xlint -Xmaxwarns 1000 -J-Xmx800m ${JAVAC_TARGET}
# When using the JSR 308 compiler, this expects checkers-quals.jar on the classpath.
# The "-classpath" argument is only necessary when using the JSR 308 javac,
# since it's a shell command for "javac -jar ..." which ignores the
# CLASSPATH environment variable and thus must be supplied explicitly.
JAVAC_COMMAND = ${JAVAC} ${JAVAC_ARGS} ${JAVAC_EXTRA_ARGS}
# JAVAC ?= javac -g -Xlint:unchecked
# JAVAC ?= jikes -g +E +F
JAVA ?= java -ea
JAVADOC ?= javadoc

JAR ?= jar

# Leading "./" is for consistency with output of "find", so that names are
# identical and "sort" can remove duplicates.
AUTO_GENERATED_FILES := \
	src/plume/MathMDE.java
# Call to sort is to remove duplicates, which would cause javac errors.
# Uners may override to compile a subset of files.  E.g., src/plume/JWhich.java
JAVA_FILES ?= $(sort $(shell find src \( -name CVS \) -prune -o -name '*.java' -print | grep -v '\.\#' | sort) ${AUTO_GENERATED_FILES})
JAVA_CPP_FILES := $(shell find src \( -name CVS \) -prune -o -name '*.java.jpp' -print | sort)
JAVA_AND_CPP_FILES := ${JAVA_FILES} ${JAVA_CPP_FILES}

CHECKERS ?= ${HOME}/research/types/checker-framework/checkers
jsr308_imports=checkers.interning.quals.*:checkers.nullness.quals.*


export jsr308_imports

default: jar
compile: all

all: compile_without_testing test

compile_without_testing:  .class-files-timestamp

# Don't do this.  We want to create .class-files-timestamp after the
# compilation succeeds.  So, no targets for .class files in this Makefile.
# ${ALL_CLASS_FILES}: .class-files-timestamp

# This rule creates ${ALL_CLASS_FILES}.
# The timestamp file indicates when the files were last compiled.
.class-files-timestamp: ${JAVA_AND_CPP_FILES}
	${JAVAC_COMMAND} ${JAVAC_EXTRA_ARGS} ${JAVA_FILES} 2>&1
	touch .class-files-timestamp

clean:
	rm -f src/plume/*.class
	rm -f .class-files-timestamp

very_clean: clean
	rm -f ${AUTO_GENERATED_FILES}

tags: TAGS
TAGS: ${JAVA_AND_CPP_FILES}
	etags ${JAVA_AND_CPP_FILES}

# This "set" form doesn't seem to work for me (maybe needs to be export?).
# Used to depend on targets "clean all", but I want to run this in "all".
test: .test-timestamp

# Using compile_without_testing instead of .class-files-timestamp as the
# prerequisite doesn't work.
.test-timestamp: .class-files-timestamp
# CLASSPATH is set in this file; it is not an external dependency
	set JAVA_COMPILER=NONE; ${JAVA} plume.TestPlume
	touch .test-timestamp

run-chicory:
	set JAVA_COMPILER=NONE; ${JAVA} -ea daikon.Chicory --output-dir=/tmp -- plume.TestPlume --shortrun

run-chicory-arrays:
	set JAVA_COMPILER=NONE; ${JAVA} -ea daikon.Chicory --ppt-select-pattern=ArraysMDE --output-dir=/tmp plume.TestPlume --shortrun


doc: javadoc
javadoc: .javadoc-timestamp

.javadoc-timestamp: $(JAVA_FILES)
	mkdir -p api
	${JAVADOC} -d api ${JAVA_FILES}
	# jdk-add-munged-anchor-names api/plume/*.html
	touch .javadoc-timestamp
	@echo "Optionally run jdk-index-to-alist (for Emacs support)"

showvars:
	@echo JAVA_AND_CPP_FILES = $(JAVA_AND_CPP_FILES)
	@echo JAVA_FILES = $(JAVA_FILES)
	@echo JAVA_CPP_FILES = $(JAVA_CPP_FILES)
	@echo CLASSPATH = "$(CLASSPATH)"
	@echo jsr308_imports = $(jsr308_imports)


###########################################################################
### Automatically generated files
###

src/plume/MathMDE.java:
	$(MAKE) -C src/plume MathMDE.java


###########################################################################
### Jar files and distribution
###

## TODO:  the .jar files other than plume.jar should be trimmed, with
## extraneous components removed.


jar: plume.jar
# This should depend on all the included .jar files, too.
# TODO: Why does this depend on lookup.html and lookup.txt but doesn't include them?
plume.jar: .class-files-timestamp .test-timestamp lib lookup.html lookup.txt
# "make clean" avoids including stray .class files from your directory.
# "make compile" runs unit tests; do that instead of "compile_without_testing".
# If you use "compile_without_testing", the tests will look out of date
# with respect to the newly-generated .class files.
# But, don't make "clean compile" the prerequisites for plume.jar or the
# .jar file will always be remade even if it is up-to-date.
	$(MAKE) clean compile
	rm -rf jar-contents
	mkdir jar-contents
	mkdir jar-contents/plume
	cp -p src/plume/*.class jar-contents/plume
	(cd jar-contents; jar xf ../lib/bcel.jar)
	(cd jar-contents; jar xf ../lib/commons-logging-1.1.1.jar)
	(cd jar-contents; jar xf ../lib/commons-io-1.4.jar)
	(cd jar-contents; jar xf ../lib/commons-lang-2.4.jar)
	(cd jar-contents; jar xf ../lib/checkers-quals.jar)
	(cd jar-contents; jar xf ../lib/ical4j.jar)
	(cd jar-contents; jar xf ../lib/ini4j-0.5.1.jar)
	(cd jar-contents; jar xf ../lib/junit.jar)
	(cd jar-contents; jar xf ../lib/svnkit.jar)
	(cd jar-contents; jar xf ../lib/tagsoup-1.2.jar)
	(cd jar-contents; jar xf ../lib/xom-1.2.1.jar)
	(cd jar-contents; jar xf $(TOOLS_JAR) com/sun/javadoc)
	rm -rf jar-contents/meta-inf jar-contents/META-INF
# Put contents in alphabetical order. (not for now, overflows command line)
#	(cd jar-contents; jar cf ../plume.jar `find * -type f | sort`)
	cd jar-contents; jar cf ../plume.jar *
	rm -rf jar-contents

#
# Lookup targets
#
lookup.html: src/plume/Lookup.java
	${JAVADOC} -docletpath .. -J-ea -quiet -doclet plume.Lookup $< > $@
lookup.txt: lookup.html
# Note no space between command and argument.
	$(HTML_TO_TEXT)$< > $@

lookup.jar : plume.jar lookup.manifest
	rm -rf jar-contents
	mkdir jar-contents
	(cd jar-contents; jar xf ../plume.jar)
	rm -rf jar-contents/meta-inf jar-contents/META-INF
	(cd jar-contents; $(JAR) cfm ../lookup.jar ../lookup.manifest `find * -type f | sort`)
	rm -rf jar-contents

install-lookup: $(pag)/software/pkg/lookup.jar
$(pag)/software/pkg/lookup.jar: lookup.jar
	cp -p $< $>

#
# TaskManager targets
#
TASKMANAGER_JAVA_FILES := $(shell find . -name '*.java')
task-manager : task_manager.jar
task_manager.jar: plume.jar task_manager.manifest
	rm -rf jar-contents
	mkdir jar-contents
	(cd jar-contents; jar xf ../plume.jar)
	rm -rf jar-contents/meta-inf jar-contents/META-INF
	(cd jar-contents; $(JAR) cfm ../task_manager.jar ../task_manager.manifest `find * -type f | sort`)
	rm -rf jar-contents

install-task-manager: $(pag)/software/pkg/task_manager.jar
$(pag)/software/pkg/task_manager.jar: task_manager.jar
	cp -p $< $>


# No need to do "make doc" or "make all" before running this.
# (This includes .class files, so users don't need to recompile.)
plume.tar.gz: $(JAVA_AND_CPP_FILES) Makefile README
	rm -rf /tmp/plume
	mkdir /tmp/plume
	cp -p $(JAVA_AND_CPP_FILES) /tmp/plume
	cp -p Makefile README /tmp/plume
	cp -r lib /tmp/plume
	(cd /tmp/plume; make compile_without_testing doc)
	(cd /tmp; tar zcf plume.tar.gz plume)
	rm -f plume.tar.gz
	mv /tmp/plume.tar.gz .
	rm -rf /tmp/plume



###########################################################################
### Pluggable type-checking
###

## These require checkers.jar on the classpath, not just checkers-quals.jar.
## Can do that via PLUME_CLASSPATH_PREFIX (though other ways may be cleaner).

# A user may set JAVAC_USER_NULLNESS_ARGS, for example to: -Awarns
JAVAC_NULLNESS_ARGS = -processor checkers.nullness.NullnessChecker -implicit:class -Xlint:-processing
CHECKERS_BOOTCLASSPATH = -Xbootclasspath/p:${CHECKERS}/jdk/jdk.jar

check-nullness:
	${JAVAC_COMMAND} ${JAVAC_USER_NULLNESS_ARGS} ${JAVAC_NULLNESS_ARGS} ${CHECKERS_BOOTCLASSPATH} ${JAVA_FILES}

# A user may set JAVAC_USER_INTERNING_ARGS, for example to: -Awarns
JAVAC_INTERNING_ARGS = -processor checkers.interning.InterningChecker -implicit:class -Xlint:-processing

check-interning:
	${JAVAC_COMMAND} ${JAVAC_USER_INTERNING_ARGS} ${JAVAC_INTERNING_ARGS} ${JAVA_FILES}



###########################################################################
### Makefile bookkeeping
###

.PHONY: default all compile_without_testing jar clean very_clean tags test run-chicory javadoc showvars doc task-manager install-task-manager install-lookup


# Existence of this rule means that a target is deleted if has just changed
# and its rule commands exit with nonzero status.  It's needed for the
# java-cpp rules above.
# (Actually, it isn't anymore, for I arrange not to overwrite the file
# unless success.  But leave it in for now anyway.)

.DELETE_ON_ERROR:

# end
