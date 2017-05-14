#注意：应该确保所有配置中没有这句：#
#-dontoptimize
#
#需要启用下面这些：#
##__________________________________________________
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
-optimizationpasses 5
-allowaccessmodification

-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose
-dontpreverify
