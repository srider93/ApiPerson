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



TestData data_init = findTestData('Data Files/DDT_Person')
def rows_lign = data_init.getRowNumbers()
def rows_column = data_init.getColumnNumbers()

def list_name = new ArrayList()
for (i in 1..rows_lign) {
	list_name.add(data_init.getValue('name', i))
}
println(list_name.size)


def list_gender = new ArrayList();
for (i in 1..rows_lign) {
	list_gender.add(data_init.getValue('gender', i))
}
println(list_gender.size)


def list_BDate = new ArrayList();
for (i in 1..rows_lign) {
	list_BDate.add(data_init.getValue('BirthDate', i))
}
println(list_BDate.size)

for (i in 0..rows_lign-1) {
def gender = list_gender.get(i)
def namePerson = list_name.get(i)
def birthDate = list_BDate.get(i)
def jsonInputFile = JsonOutput.toJson([gender: gender, name: namePerson, birthDate: birthDate])
println(jsonInputFile)
def request2 = (RequestObject)findTestObject('PersonsOR/PostPerson')
request2.setBodyContent(new HttpTextBodyContent(jsonInputFile,"UTF-8", "application/json"))
def AddSinglePerson = WS.sendRequest(request2)
WS.verifyResponseStatusCode(AddSinglePerson, 200)
}


