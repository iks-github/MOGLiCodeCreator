﻿#The MOGLi Code Creator   

* * *

Just another code generator? - NO! - It's THE Lightweight autogeneration tool! 

It's provides inserting into existing files, making complete new files and building trees of files within the file system.

It's a small standalone Tool for a quick start into model based development!

It's easy to learn, to apply and to integrate in your IDE.

It's quick in execution.

It's failsafe because it supports Reverse Engineering: re-generate your artefacts as your wish.

It's written in Java but made to generate all kinds of text documents.

* * *

**MOGLi** stands for the following attributes: 

**M**  odel based

**O**  pen for extension

**G**  enerator based

**Li**  ghtweight  


* * *

License: **All Rights Reserved**

travis-ci: [![Build Status](https://travis-ci.org/iks-github/MOGLiCodeCreator.png?branch=master)](https://travis-ci.org/iks-github/MOGLiCodeCreator)

Current version: **1.5.5**

**Changes to 1.5.4**
- Improvement: Domain Model extended to higher complexity for demonstration purpose
- Improvement: MOGLiCCJavaBeanRegistry introduced to handle reciprocal referencing between domain classes
- Improvement: Template code refactored
- Bugfix: Generation of clone-Method corrected

**Changes to 1.5.3**
- Bugfix: Classpath handling in start scripts improved
- Improvement: API of the Factory classes of the MOGLiCC JavaBean Group cleaned
- Improvement: help and readme files updated

**Changes to 1.5.2**
- Bugfix: MOGLiCC JavaBean Group bugfixed: hash-Method of JavaBean corrected
- Improvement: provider.model.standard.excel allows to use letters to define columns

**Changes to 1.5.1**
- Improvement: MOGLiCC JavaBean Group now supports to configure an arbitrary namespace
- Bugfix: MOGLiCC JavaBean Group now handles "implements" metainfos correct

**Changes to 1.5.0**
- Improvement: Provider report now with validation error messages (if occurred)
- Improvement: Factory classes in the MOGLiCC JavaBean Group now support org.joda.time.DateTime

**Changes to 1.4.0**
- New Feature: New Plugin 'provider.model.standard.excel' for reading test data from excel files
- New Feature: New artefact type in the MOGLiCC JavaBean Group: Factory classes to generate instances of domain classes containing test data
- Portability: Also running von Mac OS and Ubuntu.
- A number of smaller improvements (see milestone 1.5.0 https://github.com/iks-github/MOGLiCodeCreator/issues)

**Changes to 1.3.0**
- New Feature: Generation of a report dir containing the report files "generator.report", "provider.report" and "short.report"
- New Feature: Generation of a "error.report" file in the application root dir containing information about the error occurred
- New Feature: Each plugin provides information on the "Suggested Execution Order" by which they are executed if this order does not contradict their dependencies

**Changes to 1.2.0**
- New Feature: VelocityModelBasedTreeBuilder introduced to generate whole file trees (folders containing files and subfolders)
- New Feature: ConditionalMetaInfoValidator allows is-false-conditions
- New Feature: generator property "skipGeneration" introduced
- New Feature: TemplateStringUtil has additional methods available
- New Feature: For each artefact, more than one valid model can be defined (improved reusability)
- responsibility for throwing metainfo validation exception moved from model provider to generators
- Error messages improved
- help files corrected
- MetaInfoValidation bugfixes

**Changes to 1.1.0**
- New Feature: More than one main template possible for the VelocityModelBasedInserter
- New Feature: Generation of a report file listing all generated artefacts
- DSL for the MetaInfoValidators improved
- MetaInfoValidator error messages improved
- MetaInfoValidator error messages are now written in _MOGLiCC.log


**Changes to 1.0.1**
- New property 'BraceSymbolForMetaInfoValues' for the plugin.properties file of the StandardModelProvider. By replacing the standard symbol Double Quotes <"> by another symbol Double Quotes can be used within names or values of MetaInfos. Detailed Information is available in the default _model.properties file of the StandardModelProvider.
- New template header attribute '@OutputEncodingFormat' that is used by generator plugins to create output files. See also <root dir>\help\VelocityModelBasedInserter\TemplateFileHeaderInserterAttributes.htm and <root dir>\help\VelocityClassBasedGenerator\TemplateFileHeaderInserterAttributes.htm
- New Feature when starting MOGLiCC: an argument specifying the workspace to use can be defined. If not set, the workspace to use is read from the application properies file as in version 1.0.1.See <rootDir>/startMOGLiCodeCreator.bat
- New type of MetaInfo validation introduced: MetaInfo can be validated both by number of occurrences and by MetaInfo values. In addition, validation can depend on conditions that must be met. For this purpose a specific MetaInfoValidator-DSL is introduced.Detailed Information is available in the default MetaInfo.validation file of the VelocityClassBasedGenerator.
- Since V1.1.0 plugins are deactivated by default. To activate plugins, a corresponding activation setting in the workspace.properties file must exist.
- Method 'doesHaveAnyMetaInfosWithName(metaInfoName)' added to Model, ClassDescriptor and AttributeDescriptor of StandardModelProvider.
- New model file 'MOGLiCC-Plugin' added to the StandardModelProvider
- New artefact 'MOGLiCC-Plugin" added to the VelocityClassBasedGenerator 

**Changes to 1.0.0**
- empty output file bug fixed

* * *


Versioning convention: major.minor.revision

major:    will change for basic framework modification

minor:    will change for new features and larger improvements

revision: will change for bug fixes and smaller improvements


* * *


####Markdown Documentation

you can find documentation around markdown here:
- [Daring Fireball] [1]
- [Wikipedia - markdown] [2]

  [1]: http://daringfireball.net/projects/markdown/syntax
  [2]: http://en.wikipedia.org/wiki/Markdown
