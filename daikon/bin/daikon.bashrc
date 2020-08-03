## daikon.bashrc
## Daikon initialization file for Bourne shell (bash) users.
## (This file should be kept in synch with daikon.cshrc and daikonenv.bat.)

## Wherever you source this file, you should set two environment variables:
##   DAIKONDIR      absolute pathname of the "daikon" directory
##   JAVA_HOME      absolute pathname of the directory containing the JDK
##                  (or "none" if you don't have it)
## Optionally, you may set the following environment variables:
##   DAIKONCLASS_SOURCES   to any value, if you want to run Daikon from .class
##        files, instead of the default, which is to use daikon.jar.
## You should not need to edit this file directly.

if [ -z "$JAVA_HOME" ]; then
  echo "JAVA_HOME environment variable is not set."
  echo "Bailing out of daikon.bashrc ."
  return 2
elif [ ! -d "$JAVA_HOME" -a "$JAVA_HOME" != "none" ]; then
  echo "JAVA_HOME is set to non-existent directory: $JAVA_HOME"
  echo "Bailing out of daikon.bashrc ."
  return 2
fi

if [ -z "$DAIKONDIR" ]; then
  echo "DAIKONDIR environment variable is not set."
  echo "Bailing out of daikon.bashrc ."
  return 2
elif [ ! -d "$DAIKONDIR" ]; then
  echo "DAIKONDIR is set to non-existent directory: $DAIKONDIR"
  echo "Bailing out of daikon.bashrc ."
  return 2
fi

if [ -z "$DAIKONBIN" ]; then
  if [ -d ${DAIKONDIR}/bin ]; then
    export DAIKONBIN=${DAIKONDIR}/bin
  elif [ -d ${DAIKONDIR}/scripts ]; then
    export DAIKONBIN=${DAIKONDIR}/scripts
  else
    echo "Cannot choose a value for environment variable DAIKONBIN."
    echo "Bailing out of daikon.bashrc ."
    return 2
  fi
fi

if [ -z "$PLUMEBIN" ]; then
  export PLUMEBIN=${DAIKONDIR}/plume-lib/bin
fi

# export DAIKONCLASS_SOURCES=1

if [ $DAIKONCLASS_SOURCES ]; then
  CPADD=${DAIKONDIR}/java
else
  CPADD=${DAIKONDIR}/daikon.jar
fi
if [ ! -z "$CLASSPATH" ]; then
  export CLASSPATH=${CPADD}:${CLASSPATH}
else
  export CLASSPATH=${CPADD}
fi

if [ "$JDKPATH" != "none" ]; then
  ## tools.jar must be on your classpath.
  if [ "$OSTYPE" != "darwin" ]; then
    export CLASSPATH=${CLASSPATH}:${JAVA_HOME}/jre/lib/rt.jar:${JAVA_HOME}/lib/tools.jar
  else
    ## For Macintosh MacOSX users.  (This list
    ## is the system property "sun.boot.class.path".)
    export CLASSPATH=${CLASSPATH}:/System/Library/Frameworks/JavaVM.framework/Versions/1.3.1/Classes/classes.jar:/System/Library/Frameworks/JavaVM.framework/Versions/1.3.1/Classes/ui.jar:/System/Library/Frameworks/JavaVM.framework/Versions/1.3.1/Classes/i18n.jar:/System/Library/Frameworks/JavaVM.framework/Versions/1.3.1/Classes/sunrsasign.jar
  fi

  ## Make sure the specified JDK is first on your path
  export PATH=$JAVA_HOME/bin:$PATH
fi

## Add the Daikon binaries to your path
export PATH=${DAIKONBIN}:${PLUMEBIN}:${PATH}

## Indicate where to find Perl modules such as util_daikon.pm.
if [ $PERLLIB ]; then
  export PERLLIB=${DAIKONBIN}:${PLUMEBIN}:${PERLLIB}
else
  export PERLLIB=${DAIKONBIN}:${PLUMEBIN}
fi
