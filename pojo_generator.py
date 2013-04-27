#!/usr/bin/python
import sys, os, string

def main( ) :
	os.system( 'echo "\n\n\n\n"' );	
	os.system( 'java -jar ./mybatis-generator-1.3.2/mybatis-generator-core-1.3.2.jar -configfile ./mybatis-generator-1.3.2/generator.xml -overwrite;' )
	sys.exit(0)

if __name__ == '__main__' :
    sys.exit(main())
