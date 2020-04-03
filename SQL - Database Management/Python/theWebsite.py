import psycopg2
from flask import Flask, render_template
from flask import request
app = Flask(__name__)

def getConn():
    pwFile = open("pw.txt", "r")
    pw = pwFile.read();
    pwFile.close()
    connStr = "host='cmpstudb-01.cmp.uea.ac.uk' \
               dbname= 'jmt18sfu' user='jmt18sfu' password = " + pw
    conn=psycopg2.connect(connStr)          
    return  conn
               

@app.route('/')
def home(): 
    return render_template('Homepage.html')


@app.route('/AddCategory', methods =['POST'])
def AddCategory():
    try:
        conn=None
        category_id = request.form['category_id']
        category_name = request.form['category_name']
        category_type = request.form['category_type']
        conn=getConn()
        cur = conn.cursor()
        cur.execute('SET search_path to public')
        cur.execute('INSERT INTO Category VALUES (%s, %s, %s)', \
                   [category_id, category_name, category_type])
        conn.commit()
        return render_template('Homepage.html', msg = 'Category Added')
    except Exception as e:
        return render_template('Homepage.html', msg = 'Category NOT Added', error=e)
    finally:
        if conn:
            conn.close()
            
            
@app.route('/DeleteCategory', methods = ['POST'])
def DeleteCategory():
    try:
        conn=None
        category_id = request.form['category_id']
        conn=getConn()
        cur = conn.cursor()
        cur.execute('SET search_path to public')
        cur.execute('DELETE FROM Category WHERE CategoryID = %s', \
                    [category_id])
        conn.commit()
        return render_template('Homepage.html', msg = 'The Category was successfully Removed')
    except Exception as e:
        return render_template('Homepage.html', msg = 'Category NOT Removed', error=e)
    finally:
        if conn:
            conn.close()

            
@app.route('/CategoryReport', methods =['GET'])
def CategoryReport():
    conn=None
    try:
        conn=getConn()
        cur = conn.cursor()
        cur.execute('SET search_path to public')
        cur.execute("SELECT Category.Name, count(Book.Title) AS Title_Count, Cast(AVG(Book.Price) AS DECIMAL(10,2)) AS Average_Price FROM Category JOIN Book ON Category.CategoryID=Book.CategoryID GROUP BY Category.Name UNION ALL SELECT 'TOTAL', count(Book.Title), SUM(Book.Price) FROM Book JOIN Category ON Book.CategoryID=Category.CategoryID")
        rows = cur.fetchall()
        return render_template('CategoryReport.html', rows=rows)
    except Exception as e:
        return render_template('CategoryReport.html', msg = 'Error in Database', error=e)
    finally:
        if conn:
            conn.close()

            
            
            
@app.route('/BookOrderHistory', methods =['GET', 'POST'])
def BookOrderHistory():
    conn=None
    try:
        book_id = request.form['book_id']
        conn=getConn()
        cur = conn.cursor()
        cur.execute('SET search_path to public')
        cur.execute("SELECT Orderline.BookID, Book.Title, Book.Price, Orderline.ShopOrderID, Orderline.Quantity, Orderline.UnitSellingPrice, ShopOrder.Orderdate, Shop.Name, ShopOrder.ShopOrderID FROM Orderline JOIN Book ON Orderline.BookID = Book.BookID JOIN ShopOrder ON Orderline.ShopOrderID = ShopOrder.ShopOrderID JOIN Shop ON ShopOrder.ShopID = Shop.ShopID WHERE Book.BookID = (%s) UNION ALL SELECT NULL, 'TOTAL', SUM(Book.Price), NULL, SUM(Quantity), NULL, NULL, NULL, NULL FROM Orderline JOIN Book ON Orderline.BookID = Book.BookID JOIN ShopOrder ON Orderline.ShopOrderID = ShopOrder.ShopOrderID JOIN Shop ON ShopOrder.ShopID = Shop.ShopID Where Book.BookID = (%s)",[book_id])
        rows = cur.fetchall()
        return render_template('BookOrderHistory.html', rows=rows)
    except Exception as e:
        return render_template('BookOrderHistory.html', msg = 'Error in Database', error=e)
    finally:
        if conn:
            conn.close()
            

if __name__ == "__main__":
    app.run(debug = True)