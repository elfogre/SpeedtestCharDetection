Times with 200k random string half of them with 40 chars length and the other half 4000 chars length. Approximately half of them OK:

	checkWithJava8 -> 9,973s
	checkWithRegex -> 8,076s
	checkWithCompiledRegex -> 8,090s
	checkWithCharBucle -> 7,864s
	checkWithCharBucleOptimized -> 5,474s

Same test but all strings with 40 chars length:

        checkWithJava8 -> 0,447s
        checkWithRegex -> 0,456s
        checkWithCompiledRegex -> 0,360s
        checkWithCharBucle -> 0,290s
        checkWithCharBucleOptimized -> 0,790s

Same tests but all strings with 4000 chars length:

        checkWithJava8 -> 131,826s
        checkWithRegex -> 17,243s
        checkWithCompiledRegex -> 17,787s
        checkWithCharBucle -> 122,594s
        checkWithCharBucleOptimized -> 34,419s
