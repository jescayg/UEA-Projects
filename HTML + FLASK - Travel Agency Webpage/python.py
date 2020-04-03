from flask import Flask, render_template
from flask import request
import datetime
import csv
app = Flask(__name__)
@app.route('/')
def home(): 
    return render_template('homepage.html')
    
@app.route('/locAtt')
def locAtt():
	return render_template('locAtt.html') 
    
@app.route('/bookings')
def bookings():
	bookingsFile = 'static\\bookings.csv' 
	bookings = readFile(bookingsFile)
	
	return render_template('bookings.html', bookings=bookings)
    
@app.route('/addbooking', methods = ['POST']) 
def addbooking():
	bookingsFile = 'static\\bookings.csv'
	bookings = readFile(bookingsFile)
	
	bookingsName = request.form[('name')]
	bookingsEmail = request.form[('email')]
	checkIn = request.form[('check-in')]
	checkOut = request.form[('check-out')]
	
	year,month,day = checkIn.split('-')
	checkIn = datetime.date(int(year),int(month),int(day))
	
	year,month,day = checkOut.split('-')
	checkOut = datetime.date(int(year),int(month),int(day))
	
	format = '%d/%m/%y'
	checkIn = checkIn.strftime(format)
	checkOut = checkOut.strftime(format)

	newEntry = [bookingsName, bookingsEmail, checkIn, checkOut]
	bookings.append(newEntry)

	writeFile(bookings, bookingsFile)  
	return render_template('bookings.html', bookings=bookings)
    
    
@app.route('/reviews')
def reviews():
    with open('static\\reviews.csv', 'r') as inFile:
        reader = csv.reader(inFile)
        aList = [row for row in reader]
    return render_template('review.html', aList = aList)   

@app.route('/addreview', methods = ['POST']) 
def addreview():
	reviewsFile = 'static\\reviews.csv'
	reviews = readFile(reviewsFile)
	
	reviewersName = request.form[('name')]
	reviewDate = request.form[('date')]
	review = request.form[('review')]
	
	year,month,day = reviewDate.split('-')
	reviewDate = datetime.date(int(year),int(month),int(day))
	
	format = '%d/%m/%y'
	reviewDate = reviewDate.strftime(format)
	
	newEntry = [reviewersName, reviewDate, review]
	reviews.append(newEntry)
	
	writeFile(reviews, reviewsFile)
	return render_template('review.html', aList=reviews)
	
	
def readFile(aFile): 
    with open(aFile, 'r') as inFile: 
        reader = csv.reader(inFile) 
        bookings = [row for row in reader] 
    return bookings

def writeFile(aList, aFile): 
    with open(aFile, 'w', newline='') as outFile: 
        writer = csv.writer(outFile) 
        writer.writerows(aList)      
    return

if __name__ == '__main__':
    app.run(debug = True)