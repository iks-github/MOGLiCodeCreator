echo off
echo *
echo *
echo *
echo *
echo *     ***********************************************************
echo *     ***********************************************************
echo *     ***********************************************************
echo *     *****                                                 *****
echo *     *****                      Step 1:                    *****
echo *     *****                  PREPARE TESTING                *****
echo *     *****     mvn clean install on application module     *****
echo *     *****                                                 *****
echo *     ***********************************************************
echo *     ***********************************************************
echo *     ***********************************************************
echo *
echo *
echo *
echo *


cd ..\..
call mvn clean install -Dtest=BuildTestExecutor

echo *


pause
