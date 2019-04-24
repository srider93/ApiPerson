import com.kms.katalon.core.annotation.SetUp
import com.kms.katalon.core.annotation.SetupTestCase
import com.kms.katalon.core.annotation.TearDown
import com.kms.katalon.core.annotation.TearDownTestCase
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory as CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as MobileBuiltInKeywords
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testcase.TestCaseFactory as TestCaseFactory
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testdata.TestDataFactory as TestDataFactory
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.HttpBodyContent
import com.kms.katalon.core.testobject.ObjectRepository as ObjectRepository
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WSBuiltInKeywords
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUiBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.impl.HttpTextBodyContent
import java.util.List
import java.util.concurrent.locks.Condition
import java.util.ArrayList
import groovy.json.JsonOutput

/**
 * Some methods below are samples for using SetUp/TearDown in a test suite.
 */

/**
 * Setup test suite environment.
 */

// TODO create class Person and Gender (cf server)
enum Gender{
	MR, MRS
  }


class Person {
	String name
	String BirthDate
	Gender gender

	Person(Gender gender, String name, String BirthDate) {
		this.gender = gender
		this.name = name
		this.BirthDate = BirthDate
	}
}



@SetUp(skipped = false) // Please change skipped to be false to activate this method.
def setUp() {
		
	TestData data_init = findTestData('Data Files/DDT_Person')
	def rows_lign = data_init.getRowNumbers()
// for each line // TODO find a way to make it work (properly)
//	for (i in 0..rows_lign-1) {
//	def name = list_name.get(i)
//	def gender = list_gender.get(i)
//	def BDate = list_BDate.get(i)
//	def AddSinglePerson = WS.sendRequest(findTestObject('PersonsOR/PostPerson', [('gender') : gender, ('name') : name
//		, ('dateBD') : BDate]))
//	WS.verifyResponseStatusCode(AddSinglePerson, 200)
//	}
	
	// for each line // TODO find a way to use an object
	for (i in 1..rows_lign-1) {
		def gender = (Gender) data_init.getValue('gender', i)
		def name = data_init.getValue('name', i)
		def BirthDate = data_init.getValue('BirthDate', i)
		def person1 = new Person(gender, name, BirthDate)
		def AddSinglePerson = WS.sendRequest(findTestObject('PersonsOR/PostPerson', [('gender') : person1.gender, ('name') : person1.name
			, ('dateBD') : person1.BirthDate]))
		WS.verifyResponseStatusCode(AddSinglePerson, 200)
		}
	
}

/**
 * Clean test suites environment.
 */
@TearDown(skipped = false) // Please change skipped to be false to activate this method.
def tearDown() {

	TestData data_init1 = findTestData('Data Files/DDT_Person')
	def rows_lign = data_init1.getRowNumbers()
	def rows_column = data_init1.getColumnNumbers()
	def list_name1 = new ArrayList()
	for (i in 1..rows_lign) {
		list_name1.add(data_init1.getValue('name', i))
	}

	for (i in 0..rows_lign-1) {
		def namePersonSup = list_name1.get(i)
		def deleteListperson = WS.sendRequest(findTestObject('PersonsOR/DeleteOnePerson', [('OnePerson') : namePersonSup]))
		WS.verifyResponseStatusCode(deleteListperson, 204)
	}
	}


/**
 * Run before each test case starts.
 */
@SetupTestCase(skipped = false) // Please change skipped to be false to activate this method.
def setupTestCase() {
	GlobalVariable.valueExecSuiteTest = 1
}

/**
 * Run after each test case ends.
 */
@TearDownTestCase(skipped = true) // Please change skipped to be false to activate this method.
def tearDownTestCase() {
	GlobalVariable.valueExecSuiteTest = 0
}

/**
 * References:
 * Groovy tutorial page: http://docs.groovy-lang.org/next/html/documentation/
 */