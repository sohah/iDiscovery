# Main Makefile for Daikon

all:
	$(MAKE) -C bin
ifdef DAIKONCLASS_SOURCES
	$(MAKE) -C java
endif
	$(MAKE) java/dcomp_rt.jar
ifeq (Linux i686,$(shell uname -sm))
	$(MAKE) kvasir
else
ifeq (Linux i586,$(shell uname -sm))
	$(MAKE) kvasir
else
ifeq (Linux i486,$(shell uname -sm))
	$(MAKE) kvasir
else
ifeq (Linux i386,$(shell uname -sm))
	$(MAKE) kvasir
else
ifeq (Linux x86_64,$(shell uname -sm))
	$(MAKE) kvasir
else
	@echo "Not building Kvasir: it's only for Linux x86 and x86-64"
	@echo "and this appears to be" `uname -sm`
endif
endif
endif
endif
endif

clean:
	$(MAKE) -C bin clean
ifdef DAIKONCLASS_SOURCES
	$(MAKE) -C java clean
endif
	$(MAKE) -C kvasir/valgrind clean

java/dcomp_rt.jar:
	$(MAKE) -C java dcomp_rt.jar

kvasir:
	cd kvasir/valgrind && ./configure --prefix=`pwd`/../inst
	cd kvasir/valgrind && make
	cd kvasir/valgrind && make install

.PHONY: kvasir

plume-lib:
	rm -rf java/utilMDE java/lib/utilMDE.jar
	hg clone https://plume-lib.googlecode.com/hg/ plume-lib

# the plume-lib in the distribution is an archive and can't be updated
.PHONY: plume-lib-update
plume-lib-update: plume-lib
	#(cd plume-lib; hg pull -u)

# The file is real, but the commands should always be re-run even if it exists.
.PHONY: plume-lib/java/plume.jar
plume-lib/java/plume.jar: plume-lib-update
	make -C plume-lib/java plume.jar CLASSPATH=.:./daikon.jar
