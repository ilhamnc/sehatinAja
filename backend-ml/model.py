import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.linear_model import LogisticRegression
from sklearn.impute import SimpleImputer
from sklearn.preprocessing import StandardScaler
import pickle

food = pd.read_csv("https://raw.githubusercontent.com/DanielWiranto/test1/main/df_food.csv")
print(food.head())

numeric_cols_with_stripes = ['Calories', 'Protein']
for col in numeric_cols_with_stripes:
    if food[col].dtype == 'object':
        food[col] = pd.to_numeric(food[col].str.strip().replace(',', ''), errors='coerce')
    
y = food['Food']
x = food.drop(columns=['Food', 'Measure', 'Category', 'Grams', 'Fat', 'Sat.Fat', 'Fiber', 'Carbs'])
imputer = SimpleImputer(strategy='median')
x_imputed = imputer.fit_transform(x)
scaler = StandardScaler()
x_scaled = scaler.fit_transform(x_imputed)
x_train, x_test, y_train, y_test = train_test_split(x_scaled, y, test_size=0.3, random_state=42)

model = LogisticRegression(max_iter=1000)
model.fit(x_train, y_train)

pickle.dump(model, open('model.pkl', 'wb'))