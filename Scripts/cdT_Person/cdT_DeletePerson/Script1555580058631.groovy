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
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.testobject.HttpBodyContent as HttpBodyContent
import com.kms.katalon.core.testobject.ObjectRepository as ObjectRepository
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.testobject.TestObjectProperty as TestObjectProperty
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WSBuiltInKeywords
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUiBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.testobject.ResponseObject as ResponseObject
import com.kms.katalon.core.testobject.RequestObject as RequestObject
import com.kms.katalon.core.testobject.impl.HttpTextBodyContent as HttpTextBodyContent
import java.util.List as List
import java.util.concurrent.locks.Condition as Condition
import java.util.ArrayList as ArrayList
import groovy.json.JsonOutput as JsonOutput

//Ajoute une personne lamda (dans notre cas, la personne ne nomme Jean) pour le test de suppression et vérifie qu'il retourne bien le code 200
def AddSinglePerson = WS.sendRequest(findTestObject('PersonsOR/PostPerson', [('gender') : GlobalVariable.gender, ('name') : GlobalVariable.namePerson
            , ('dateBD') : GlobalVariable.birthDate]))
def slurper = new groovy.json.JsonSlurper()
def result = slurper.parseText(AddSinglePerson.getResponseBodyContent())
assert result.name == GlobalVariable.namePerson
assert result.gender == GlobalVariable.gender
assert result.birthDate == GlobalVariable.birthDate
WS.verifyResponseStatusCode(AddSinglePerson, 204)//expected 200

//Verifie si la personne a bien été ajoutée dans la liste de personne et retourne bien le code 200
def checkIfPersonWellAdded = WS.sendRequest(findTestObject('PersonsOR/GetOnePerson'))
def slurper1 = new groovy.json.JsonSlurper()
def result1 = slurper1.parseText(checkIfPersonWellAdded.getResponseBodyContent())
assert result.name == GlobalVariable.namePerson
assert result.gender == GlobalVariable.gender
assert result.birthDate == GlobalVariable.birthDate
WS.verifyResponseStatusCode(checkIfPersonWellAdded, 204)//expected 200

//Supprime la donnée rentrée précédemment et verifie qu'il retourne bien le code 204
def DeletePersonAddedLastely = WS.sendRequest(findTestObject('PersonsOR/DeleteOnePerson'))
WS.verifyResponseStatusCode(DeletePersonAddedLastely, 204)

//Supprime la donnée à nouveau et verifie qu'il retourne bien le code erreur 404
def DeletePersonAgain = WS.sendRequest(findTestObject('PersonsOR/DeleteOnePerson'))
WS.verifyResponseStatusCode(DeletePersonAgain, 404)


