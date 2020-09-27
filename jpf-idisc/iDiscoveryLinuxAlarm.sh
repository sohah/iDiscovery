#!/bin/bash

#$1 is which specific technique to use for symbolic execution pruning

#import configurations
source ./iDiscoveryLinuxAlarm.cfg

now1=$(date +"%T")
echo "Current time : $now1"



#indicate wether assertion separation optimization is applied
sep=false
#indicate wether violation restriction optimization is applied
res=false
#indicate wether green solution reuse optimization is applied
gre=false

#read arguments from cmd line
while getopts srg opt
do case "$opt" in
        s) sep=true;;
        r) res=true;;
        g) gre=true;;
        esac
done

#determine the output directory name and store in "out"
if [ $sep = "true" ]
then
    out="separation"
else
    out="unseparation"
fi

if [ $res = "true" ]
then
    out="$out-restriction"
else
    out="$out-unrestriction"
fi

if [ $gre = "true" ]
then
    out="$out-green"
else
    out="$out-ungreen"
fi

#clear old traces if possible
rm traces/*
#clear green cache if possible
#/Users/zhanglingming/Research/tools/redis-2.6.14/src/redis-cli flushall
#create results dir if not exist
mkdir results
#create results dir for the specific technique
mkdir results/$out
mkdir results/$out/invariants
mkdir results/$out/symbc

#execute daikon for collecting raw traces
echo ">>run daikon trace collection for the initial tests"
cp $origjavafile $genjavafile
ant -f $targetbuild build
#java -Xmx1024m -ea -jar lib/RunJPF.jar $sub_jpf > symbolic_output.txt

java $JVM_FLAGS -jar lib/RunJPF.jar $sub_jpf > symbolic_output.txt
java -cp $idiscdir/build/main edu.utexas.gsoc.symbc.SymbcInputGenerator symbolic_output.txt  run_daikon_sym.sh 0 "$daikondir" $sub $fullsub
mv symbolic_output.txt results/$out/symbc/symbolic_output_iter_0.txt
chmod 777 run_daikon_sym.sh
./run_daikon_sym.sh $source_bin


#go for main iterations
it=1
while true; do
#analyze the daikon traces, generate "daikon_invariants.txt" file which encodes all daikon invariants
echo ">>analyze daikon traces for possible invariants"
java -cp "/home/soha/git/iDiscovery/daikon/daikon.jar" daikon.Daikon traces/*.dtrace.gz --format java > daikon_invariants.txt

#synthsize assersions for Java files based on "daikon_invariant.txt", output instrumented version of program with assertions
echo ">>synthesize assertions for original program"
#echo "java -cp "$idiscdir/build/main:$idiscdir/lib/*" edu.utexas.gsoc.inv.instrument.AssertionSynthesis "$origjavafile" "$genjavafile" "daikon_invariants.txt" "$meth" $out"

java -cp "$idiscdir/build/main:$idiscdir/lib/*" edu.utexas.gsoc.inv.instrument.AssertionSynthesis "$origjavafile" "$genjavafile" "daikon_invariants.txt" "$meth" $out

#if [ 1 -eq 0 ]; then

#rebuild the source code for jpf-symbc
ant -f $targetbuild build

#rename invariants result according to the iteration
mv daikon_invariants.txt results/$out/invariants/daikon_invariants_iter_$it.txt

#terminates the algorithm if it reaches the fix point
if [ $it -gt 1 ]; then
#extract the used invariants for comparison
java -cp "$idiscdir/build/main:$idiscdir/lib/*" edu.utexas.gsoc.inv.InvariantExtractor results/$out/invariants/daikon_invariants_iter_$it.txt "$meth" cur_invariants.txt
java -cp "$idiscdir/build/main:$idiscdir/lib/*" edu.utexas.gsoc.inv.InvariantExtractor results/$out/invariants/daikon_invariants_iter_$((it-1)).txt "$meth" pre_invariants.txt
#check the equivalence
if diff -q cur_invariants.txt pre_invariants.txt; then
  echo ">> Fixed-point reached"
  now2=$(date +"%T")
  echo "Current time : $now2"
  echo "total time = $now2-$now1"

break
fi
fi

#run symbolic execution against the program with generated assertions
echo ">>symbolic execition for program with invariant assertions"
if [[ $out == *-ungreen ]]
then
    java $JVM_FLAGS -jar lib/RunJPF.jar $sub_jpf > symbolic_output.txt
else
    java $JVM_FLAGS -jar lib/RunJPF.jar $sub_green_jpf > symbolic_output.txt
fi


#collect test inputs from symbolic execution results
echo ">>generate new daikon tracing cmds"
java -cp $idiscdir/build/main edu.utexas.gsoc.symbc.SymbcInputGenerator symbolic_output.txt  feedback_run_daikon.sh $it "$daikondir" $sub $fullsub

#store symbolic tests and time log
mv symbolic_output.txt results/$out/symbc/symbolic_output_iter_$it.txt

#execute daikon for collecting additional traces for the new inputs generated by an additional iteration of symbolic execution
echo ">>run daikon trace collection for additional tests in this iteration"
chmod 777 feedback_run_daikon.sh
./feedback_run_daikon.sh $source_bin
((it++))
done
#fi
