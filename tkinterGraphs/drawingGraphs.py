from tkinter import *
import pandas as pd
import matplotlib.pyplot as plt
from matplotlib.figure import Figure
from matplotlib.backends.backend_tkagg import (FigureCanvasTkAgg,
NavigationToolbar2Tk)

# plot function is created for
# plotting the graph in
# tkinter window
def plot():

	# plotting the graph
    plot_x = df[x_col] # get that column from the df
    plot_y = df[y_col] # get that column from the df
    
    # set graph
    ax1.scatter(plot_x, plot_y, color = 'g')
    
    # set titles/labels
    ax1.set_xlabel(x_col)
    ax1.set_ylabel(y_col)
    ax1.set_title(x_col +  ' vs ' + y_col)


    # something here
    scatter1 = FigureCanvasTkAgg(fig, window) 
    scatter1.draw()
    scatter1.get_tk_widget().pack(side=TOP, fill=BOTH)


def on_click():
    global x_col, y_col
    x_col = dropdown1.get() # get list1 answer
    y_col = dropdown2.get() # get list2 answer

    plot()

   
# dataset chosen for project
dataset = pd.read_csv("house-prices.csv")
df = pd.DataFrame(dataset)


# set column names for list
columns1 = df.columns
columns2 = df.columns

# the main Tkinter window
window = Tk()

#creation of the dropdown lists
dropdown1 = StringVar(window)
dropdown1.set(columns1[0]) # default value
firstList = OptionMenu(window, dropdown1, *columns1)

dropdown2 = StringVar(window)
dropdown2.set(columns2[0]) # default value
secondList = OptionMenu(window, dropdown2, *columns2)

# setting the title
window.title('Plotting in Tkinter')

# dimensions of the main window
window.geometry("600x600")

# the figure that will contain the plot
fig = Figure(figsize = (6,6), dpi = 75)

# adding the subplot
ax1 = fig.add_subplot(111)

# button that displays the plot
plot_button = Button(master = window,
					command = on_click,
					height = 1,
					width = 10,
					text = "Plot")

# place the button in main window
plot_button.pack()
firstList.pack()
secondList.pack()

# run the gui
window.mainloop()
