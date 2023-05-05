Disclaimer: Clean code refactoring only for the GItem --> GroceryItem.java class.

First of all we decided to change the class name from “GItem” to “GroceryItem”. Normally it would be clear for us for what “GItem” stands for, but if we would work on the project after a long break again it could be having a speaking-name for the class.

The next change was in line 4 (old code) changing the variable “checked” to “isChecked”. We thought that this would also be a better indicator for being a boolean variable.

We also changed in line 5 (old code) the variable name from “ItemName” to “itemName”. So the standard is to write variables in small capitals, thatswhy this change was inevitable. Another small change is turning this variable to final.

After this change it was also necessary to edit the parameter variable from the constructor class “String Itemname” to “String itemName” and also “boolean checked” to “boolean isChecked”.

The last minimal change is the vertical alignment in the whole class. Meaning by that is a space after the brackets opening from a class/method.
