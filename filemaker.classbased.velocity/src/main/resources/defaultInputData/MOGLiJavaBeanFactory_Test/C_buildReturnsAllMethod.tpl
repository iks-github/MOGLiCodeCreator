'	@Test
'	public void returnsAll() {
'		final List<${classDescriptor.simpleName}> result = ${classDescriptor.simpleName}Factory.createAllFromDataPool();
'		assertNotNull("Not null expected for ", result);
'		assertEquals("result", ${classDescriptor.simpleName}Factory.getNumberOfTestObjectsInDataPool(), result.size());
'
'		if ( ${classDescriptor.simpleName}Factory.getNumberOfTestObjectsInDataPool() == 0 )
'		{
'			return;  // Test not possible due to missing data
'		}
'
'		final ${classDescriptor.simpleName} last${classDescriptor.simpleName} = result.get(result.size() - 1);

		#set( $useFirstFromDataPool = "false" )
		#set( $useLastFromDataPool = "true" )
		#parse("commonSubtemplates/generateListOfDomainObjectsFromExampleDataOrDataPool.tpl")

		#foreach($attributeDescriptor in $classDescriptor.attributeDescriptorList)
		
			#set( $AttributeName = $TemplateStringUtility.firstToUpperCase($attributeDescriptor.name) )
			#set( $javaType = $attributeDescriptor.getMetaInfoValueFor("JavaType") )
			#set( $metaInfoList = $attributeDescriptor.getMetaInfosWithNameStartingWith( ${classDescriptor.simpleName} ) )
			
			#if( $metaInfoList.size() > 0 )

				#set( $lastIndex = $metaInfoList.size() - 1 )
				
		  		#set( $testDataMetaInfoValue = $metaInfoList.get($lastIndex).getValue() )
				
				#if ( $TemplateJavaUtility.isJavaMetaTypePrimitive($javaType) )
	
					'		assertEquals("$AttributeName of type $javaType", "$testDataMetaInfoValue", "" + last${classDescriptor.simpleName}.get${AttributeName}() );
				
				#else
				
					#if( $testDataMetaInfoValue == "" )
					
						'		assertEquals("$AttributeName of type $javaType", null, last${classDescriptor.simpleName}.get${AttributeName}() ); 
					
					#else
					
						#parse("commonSubtemplates/isJavaTypeDomainObject.tpl")
						#set( $enumList = $classDescriptor.getAllMetaInfoValuesFor("Enum") )
						
						#if ( $TemplateStringUtility.contains($enumList, $javaType) )
						
							'		assertEquals("$AttributeName of type $javaType", "$testDataMetaInfoValue", last${classDescriptor.simpleName}.get${AttributeName}().name() );
							
						
						#elseif ( $isJavaTypeDomainObject.equals( "true" ) )
						
							'		assertEquals("$AttributeName of type $javaType", ${javaType}Factory.createById("$testDataMetaInfoValue").toString(), last${classDescriptor.simpleName}.get${AttributeName}().toString());
								
						#elseif ( $javaType == "String" )
						
							'		assertEquals("$AttributeName of type $javaType", "$testDataMetaInfoValue", last${classDescriptor.simpleName}.get${AttributeName}() );
							
						#elseif ( $javaType == "java.math.BigDecimal" ||  $javaType == "BigDecimal" )
						
							'		assertEquals("$AttributeName of type $javaType", "$testDataMetaInfoValue", last${classDescriptor.simpleName}.get${AttributeName}().toPlainString() );
														
						#elseif ( $TemplateJavaUtility.isPrimitiveTypeWrapper($javaType) )
			
							'		assertEquals("$AttributeName of type $javaType", "$testDataMetaInfoValue", "" + last${classDescriptor.simpleName}.get${AttributeName}() );
										
						#elseif ( $javaType == "org.joda.time.DateTime" || $javaType == "DateTime" )
					
							'		assertEquals("$AttributeName of type $javaType", dateTimeFormatter.parseDateTime( "$testDataMetaInfoValue" ), last${classDescriptor.simpleName}.get${AttributeName}() );
						
						#elseif ( $javaType == "String[]" )
								
							'		assertEquals("size of $AttributeName", CollectionsStringUtils.commaSeparatedStringToStringArray("$testDataMetaInfoValue").length, last${classDescriptor.simpleName}.get${AttributeName}().length );
							'		assertEquals("$AttributeName of type $javaType", "$testDataMetaInfoValue", CollectionsStringUtils.stringArrayToCommaSeparatedString( last${classDescriptor.simpleName}.get${AttributeName}() ));
						
						#elseif ( $javaType == "java.util.HashSet<String>" )
						
							'		assertEquals("size of $AttributeName", CollectionsStringUtils.commaSeparatedStringToHashSet("$testDataMetaInfoValue").size(), last${classDescriptor.simpleName}.get${AttributeName}().size() );
							'		assertEquals("$AttributeName of type $javaType", "$testDataMetaInfoValue", CollectionsStringUtils.stringHashSetToCommaSeparatedString( last${classDescriptor.simpleName}.get${AttributeName}() ));
									
							
						#elseif ( $TemplateJavaUtility.isJavaMetaTypeGeneric($javaType) )
							
						    	#set( $CollectionType = $TemplateJavaUtility.getCollectionMetaType($javaType) ) 
							 	
								#if ($CollectionType == "java.util.List")
					
						    		#set( $ElementType = $TemplateJavaUtility.getCollectionElementType($javaType) )
									
									#if ($ElementType == "Long")
									
										'		assertEquals("size of $AttributeName", CollectionsStringUtils.commaSeparatedStringToLongList("$testDataMetaInfoValue").size(), last${classDescriptor.simpleName}.get${AttributeName}().size() );
										'		assertEquals("$AttributeName of type $javaType", "$testDataMetaInfoValue", CollectionsStringUtils.listOfLongsToCommaSeparatedString( last${classDescriptor.simpleName}.get${AttributeName}() ));
						    		
						    		#elseif	($ElementType == "String")
						    		
										'		assertEquals("size of $AttributeName", CollectionsStringUtils.commaSeparatedStringToStringList("$testDataMetaInfoValue").size(), last${classDescriptor.simpleName}.get${AttributeName}().size() );
										'		assertEquals("$AttributeName of type $javaType", "$testDataMetaInfoValue", CollectionsStringUtils.stringListToCommaSeparatedString( last${classDescriptor.simpleName}.get${AttributeName}() ));
									    		
						    		#else
						    		
						    			#set( $elementType = $TemplateStringUtility.firstToLowerCase($ElementType) ) 
						    		
										'		assertEquals("$AttributeName of type $javaType", ${elementType}List, last${classDescriptor.simpleName}.get${AttributeName}() );						
										 
						    		#end
								
								#else
								
										'		// Unkown CollectionType: $collectionType 
									
								#end 
												
						
						#else
						
							'		assertEquals("$AttributeName of type $javaType", "$testDataMetaInfoValue", last${classDescriptor.simpleName}.get${AttributeName}() );
					
						#end			
						 
	
					#end
				
				#end
			
			#end
			
			
		#end
'	}
