#!/bin/bash
# Insert JSON file into MongoDB collection
echo "Inserting JSON file into MongoDB collection..."

mongoimport --db Java --collection items --file items.json --jsonArray

echo "JSON file inserted into MongoDB collection successfully."


# MySQL commands
echo "Creating MySQL databases..."

mysql -uroot -pmypassword -e "CREATE DATABASE IF NOT EXISTS identity_service; CREATE DATABASE IF NOT EXISTS subscription_service;"


echo "MySQL databases created successfully."



