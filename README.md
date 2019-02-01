
# Browser Hotkeys 
![Alt text](src/de/jung/luciano/images/LJ_Logo2.png)

###Here is an Example of the running Programm:

![Alt text](src/de/jung/luciano/images/AppExample.png)

###JavaFX Project to open saved Urls in your Browser


- Used a FlowPane and specialised Buttons (WebsiteButton) for good looking
- opens Website by Mouse clicking or Keyboard Search
- saves the Data in a *.txt-File
- branch "OldVersion" have the first Version used TableView -> Ugly :D

###Structure

MVC-Pattern Structure
- Model uses DataHandler, WebsiteButtons
- Controller controlls Model, View, Dialog
- Overview (Main View) uses style.css
- ApplicationFacade helps to open the Website
- save.txt created to store data or just a FilePath

###WebsiteButton

WebsiteButton is a specialised Button with an Url- and ImageUrl-String.
It has a random Color, shows the Name of the Website and shows the Website if clicked

###Functionality

- Add WebsiteButton (MenuBar->Edit->Add)
- Edit WebsiteButton (RightClickMenu->Edit)
- Remove WebsiteButton (RightClickMenu->Remove)

- Save Data (MenuBar->File->Save)
- SaveAs Data in other Path via FileChooser (MenuBar->File->SaveAs)
- Load Data via FileChooser (MenuBar->File->load)
- Exit (MenuBar->File->Exit or simply the Big Red X)

- open Website
    - click on one Button
    - filter Buttons via Keyboard Input (if only one is shown it opens it)
- open all shown Buttons (press ENTER)
- add Image to Button (RightClickMenu->Edit->Add Image)
- change Color of Button (Scroll over it)


###Relating to Keyboard Input

- ESC will reset the Input
- BACK_SPACE remove last Char of Input