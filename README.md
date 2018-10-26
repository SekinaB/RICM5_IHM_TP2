# RICM5_IHM_TP2

Marking Menu

## Members
Belguendouz Sekina and Boucherima Amina

## How To Use

* The user press the right click to display the first MarkingMenu and choose the option by releasing the click
* The user can cancel the display by releasing on the option 'Back' or if he hasn't moved.
* The user will access a dialog to choose a color when he selects the option 'Color'.
* The user will access another MarkingMenu if he goes further in the direction on the 'Tool' option, still holding on the right click.

* The user, in the second menu can choose between the tools (Rectangle, Ellipse, Pen) to draw as he wish or 'Back' if he want to abandon everything
* The user will release the right click to choose the option he wants, and hold the left click to draw as he wants.

## Regrets
* We were not able to make the original application MVC but we tried to do it with our classes

## Bugs 
* To use our MarkingMenu, it is necessary to first click on a 'tool' button of the toolbar. Then the MarkingMenu will be available. 
	** This is due too the fact that we are using the class tool as our 'controller' therefore it need to be first 'activated' to work.
* When the user right clicks and also tries to draw something using the left click, the MarkingMenu will disappear and let him draw. 
* When the user draws something and right clicks, his shape will be considered as done.
* The formula to match the label to the space in the MarkingMenu is not quite right, so the positions of the labels in the table were adapted to fit in.
