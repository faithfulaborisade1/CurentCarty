<?php
require "DataBaseConfig.php";

class DataBase
{
    public $connect;
    public $data;
    private $sql;
    protected $servername;
    protected $username;
    protected $password;
    protected $databasename;

    public function __construct()
    {
        $this->connect = null;
        $this->data = null;
        $this->sql = null;
        $dbc = new DataBaseConfig();
        $this->servername = $dbc->servername;
        $this->username = $dbc->username;
        $this->password = $dbc->password;
        $this->databasename = $dbc->databasename;
    }

    function dbConnect()
    {
        $this->connect = mysqli_connect($this->servername, $this->username, $this->password, $this->databasename);
        return $this->connect;
    }

    function prepareData($data)
    {
        return mysqli_real_escape_string($this->connect, stripslashes(htmlspecialchars($data)));
    }

    function logIn($table, $username, $password) {
    $username = $this->prepareData($username);
    $password = $this->prepareData($password);
    $this->sql = "SELECT * FROM " . $table . " WHERE username = '" . $username . "'";
    $result = mysqli_query($this->connect, $this->sql);
    if (mysqli_num_rows($result) != 0) {
        $row = mysqli_fetch_assoc($result);
        $dbusername = $row['username'];
        $dbpassword = $row['password'];
        if ($dbusername == $username && password_verify($password, $dbpassword)) {
            return $row['user_id'];  // Return user ID on successful login
        }
    }
    return false;  // Return false if login fails
}


    function signUp($table, $fullname, $email, $username, $password,$contact)
    {
        $fullname = $this->prepareData($fullname);
        $username = $this->prepareData($username);
        $password = $this->prepareData($password);
        $email = $this->prepareData($email);
        $contact = $this->prepareData($contact);
        $password = password_hash($password, PASSWORD_DEFAULT);
        $this->sql =
            "INSERT INTO " . $table . " (fullname, username, password, email,contact) VALUES ('" . $fullname . "','" . $username . "','" . $password . "','" . $email . "','" . $contact . "')";
        if (mysqli_query($this->connect, $this->sql)) {
            return true;
        } else return false;
    }


    function addProduct($table,$prod_name, $prod_pic, $category, $description,$prod_stock,$prize) {
    $prod_name = $this->prepareData($prod_name);
    $prod_pic = $this->prepareData($prod_pic);
    $category = $this->prepareData($category);
    $description = $this->prepareData($description);
    $prod_stock = $this->prepareData($$prod_stock);
    $prize = $this->prepareData($prize);

    $this->sql = "INSERT INTO " . $table . "(prod_name, prod_pic, category, description, prod_stock,prize)
                  VALUES ('" . $prod_name . "','" . $prod_pic . "','" . $category . "','" . $description . "','" . $prod_stock . ",'" . $prize . "')";

    if (mysqli_query($this->connect, $this->sql)) {
        return true;
    } else {
        return false;
    }
}




    function feedback($table, $title, $description, $client, $product)
    {
        $title = $this->prepareData($title);
        $description = $this->prepareData($description);
        $client = $this->prepareData($client);
        $product = $this->prepareData($product);
        $this->sql =
            "INSERT INTO " . $table . " (title, description, client, product) VALUES ('" . $title . "','" . $description . "','" . $client . "','" . $product . "')";
        if (mysqli_query($this->connect, $this->sql)) {
            return true;
        } else return false;
    }

 function getUserProfile($table, $username) {
    $username = $this->prepareData($username);
    $this->sql = "SELECT fullname, email, contact, username FROM " . $table . " WHERE username = '" . $username . "'";
    $result = mysqli_query($this->connect, $this->sql);
    if ($result && mysqli_num_rows($result) > 0) {
        return mysqli_fetch_assoc($result);
    } else {
        return false;
    }
}

function addPriceSurveyEntry($table, $location, $product, $barcode, $price) {
    // Prepare the data for insertion to prevent SQL injection
    $location = $this->prepareData($location);
    $product = $this->prepareData($product);
    $barcode = $this->prepareData($barcode);
    $price = $this->prepareData($price);

    // Construct the SQL query to insert the data
    $this->sql = "INSERT INTO " . $table . " (location, product, barcode, price)
                  VALUES ('" . $location . "', '" . $product . "', '" . $barcode . "', '" . $price . "')";

    // Execute the query
    if (mysqli_query($this->connect, $this->sql)) {
        return true;
    } else {
        return false;
    }
}




  function updateUserProfile($table, $username, $fullname, $email, $contact) {
    $username = $this->prepareData($username);
    $fullname = $this->prepareData($fullname);
    $email = $this->prepareData($email);
    $contact = $this->prepareData($contact);

    $this->sql = "UPDATE " . $table . " SET fullname = '$fullname', email = '$email', contact = '$contact' WHERE username = '$username'";

    if (mysqli_query($this->connect, $this->sql)) {
        return true;
    } else {
        error_log("MySQL Error: " . mysqli_error($this->connect));
        return false;
    }
}




}

?>
