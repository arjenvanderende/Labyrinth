unit MazeUnit;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, Menus, Grids, StdCtrls, ExtCtrls, ComCtrls, Spin;

type
  TMazeForm = class(TForm)
    GroupBox1: TGroupBox;
    MazeGrid: TStringGrid;
    GroupBox2: TGroupBox;
    MainMenu: TMainMenu;
    File1: TMenuItem;
    Open1: TMenuItem;
    Close1: TMenuItem;
    N1: TMenuItem;
    Exit1: TMenuItem;
    New1: TMenuItem;
    StartShape: TShape;
    EndShape: TShape;
    Label1: TLabel;
    Label2: TLabel;
    OpenDialog: TOpenDialog;
    SaveDialog: TSaveDialog;
    RadioGroup1: TRadioGroup;
    Path1Shape: TShape;
    Path2Shape: TShape;
    JoinShape: TShape;
    GroupBox3: TGroupBox;
    ShapeCurrent: TShape;
    Label3: TLabel;
    Label4: TLabel;
    Label5: TLabel;
    procedure FormCreate(Sender: TObject);
    procedure MazeGridMouseDown(Sender: TObject; Button: TMouseButton;
      Shift: TShiftState; X, Y: Integer);
    procedure BrushShapeMouseDown(Sender: TObject; Button: TMouseButton;
      Shift: TShiftState; X, Y: Integer);
    procedure MazeGridDrawCell(Sender: TObject; ACol, ARow: Integer;
      Rect: TRect; State: TGridDrawState);
    procedure MazeGridMouseMove(Sender: TObject; Shift: TShiftState; X,
      Y: Integer);
    procedure MazeGridMouseUp(Sender: TObject; Button: TMouseButton;
      Shift: TShiftState; X, Y: Integer);
    procedure Close1Click(Sender: TObject);
    procedure New1Click(Sender: TObject);
    procedure WriteHeader( var OutputFile: TextFile );
    procedure Open1Click(Sender: TObject);
    function getValue( s: String; index: Integer ): Integer;
    function getType( x: Integer; y: Integer ): String;
    procedure Exit1Click(Sender: TObject);
    procedure RadioGroup1Click(Sender: TObject);
  private
    StartPt, EndPt, StartDrag: TPoint;
    Dragging, DragButton: Boolean;
    PenType: Integer;
    LastPenType: Integer;
  public
    { Public declarations }
  end;

var
  MazeForm: TMazeForm;

const
  NODECLEAR = '';
  NODEPATH1 = '0';
  NODEPATH2 = '1';
  NODEJOINT = '2';
  NODESTART = '3';
  NODEEND   = '4';

  PENPATH1  = 0;
  PENPATH2  = 1;
  PENJOINT  = 2;
  PENSTART  = 3;
  PENEND    = 4;

  LEFTBUTTON  = FALSE;
  RIGHTBUTTON = TRUE;

implementation

{$R *.dfm}

procedure TMazeForm.FormCreate(Sender: TObject);
begin
  PenType     := PENPATH1;
  LastPenType := PENPATH1;
  Dragging    := FALSE;
  DragButton  := LEFTBUTTON;
  StartPt.X   := -1;
  StartPt.Y   := -1;
  EndPt.X     := -1;
  EndPt.Y     := -1;
end;

procedure TMazeForm.MazeGridMouseDown(Sender: TObject;
  Button: TMouseButton; Shift: TShiftState; X, Y: Integer);
var
  GrdRow, GrdCol: Integer;
begin
  // Get mousebutton
  if ( Button = mbLeft ) then
    DragButton := LEFTBUTTON
  else
    DragButton := RIGHTBUTTON;

  // Get cell coordinates
  MazeGrid.MouseToCell( X, Y, GrdCol, GrdRow );

  // Set dragging properties
  Dragging := TRUE;
  StartDrag.X := GrdCol;
  StartDrag.Y := GrdRow;

  // Set cell color
  if ( DragButton = LEFTBUTTON ) then
  begin
    // Reset start and end node if necessary
    if MazeGrid.Cells[ GrdCol, GrdRow ] = NODESTART then
    begin
      StartPt.X  := -1;
      StartPt.Y  := -1;
    end
    else if MazeGrid.Cells[ GrdCol, GrdRow ] = NODEEND then
    begin
      EndPt.X    := -1;
      EndPt.Y    := -1;
    end;
    // Check if a path-brush is set
    if ( PenType = PENPATH1 ) then
      MazeGrid.Cells[ GrdCol, GrdRow ] := NODEPATH1
    else if ( PenType = PENPATH2 ) then
      MazeGrid.Cells[ GrdCol, GrdRow ] := NODEPATH2
    else if ( PenType = PENJOINT ) then
      MazeGrid.Cells[ GrdCol, GrdRow ] := NODEJOINT
    else
    begin
      if ( PenType = PENSTART ) then
      begin
        // Check if a start point is already set, if so erase it
        if ( ( StartPt.X <> -1 ) AND
             ( MazeGrid.Cells[ StartPt.X, StartPt.Y ] = NODESTART ) ) then
          MazeGrid.Cells[ StartPt.X, StartPt.Y ] := NODECLEAR;
        // Set the start point
        MazeGrid.Cells[ GrdCol, GrdRow ] := NODESTART;
        StartPt.X := GrdCol;
        StartPt.Y := GrdRow;
      end
      else if ( PenType = PENEND ) then
      begin
        // Check if an end point is already set, if so erase it
        if ( ( EndPt.X <> -1 ) AND
             ( MazeGrid.Cells[ EndPt.X, EndPt.Y ] = NODEEND ) ) then
          MazeGrid.Cells[ EndPt.X, EndPt.Y ] := NODECLEAR;
        // Set the end point
        MazeGrid.Cells[ GrdCol, GrdRow ] := NODEEND;
        EndPt.X := GrdCol;
        EndPt.Y := GrdRow;
      end;
      // Update brush
      PenType := LastPenType;
      case ( PenType ) of
        PENPATH1:
          ShapeCurrent.Brush.Color := clFuchsia;
        PENPATH2:
          ShapeCurrent.Brush.Color := clPurple;
        PENJOINT:
          ShapeCurrent.Brush.Color := clGreen;
      end;
    end;
  end
  else
  begin
    // Check if a start or end point is being erased
    if MazeGrid.Cells[ GrdCol, GrdRow ] = NODESTART then
    begin
      StartPt.X  := -1;
      StartPt.Y  := -1;
    end
    else if MazeGrid.Cells[ GrdCol, GrdRow ] = NODEEND then
    begin
      EndPt.X    := -1;
      EndPt.Y    := -1;
    end;
    // Clear the cell
    MazeGrid.Cells[ GrdCol, GrdRow ] := NODECLEAR;
  end;
end;

procedure TMazeForm.MazeGridMouseMove(Sender: TObject; Shift: TShiftState;
  X, Y: Integer);
var
  GrdRow, GrdCol: Integer;

begin
  if ( DRAGGING ) then
  begin
    // Get cell coordinates
    MazeGrid.MouseToCell( X, Y, GrdCol, GrdRow );

    // Check if dragging is allowed
    if not ( ( GrdCol = StartDrag.X ) AND ( GrdRow = StartDrag.Y ) ) then
    begin
      // Reset dragpoint
      StartDrag.X := -1;
      StartDrag.Y := -1;

      // Check if the cell is not occupied
      if MazeGrid.Cells[ GrdCol, GrdRow ] = NODESTART then
      begin
        StartPt.X  := -1;
        StartPt.Y  := -1;
      end
      else if MazeGrid.Cells[ GrdCol, GrdRow ] = NODEEND then
      begin
        EndPt.X    := -1;
        EndPt.Y    := -1;
      end;

      if ( DragButton = LEFTBUTTON ) then
      begin
        if ( PenType = PENPATH1 ) then
          MazeGrid.Cells[ GrdCol, GrdRow ] := NODEPATH1
        else if ( PenType = PENPATH2 ) then
          MazeGrid.Cells[ GrdCol, GrdRow ] := NODEPATH2
        else if ( PenType = PENJOINT ) then
          MazeGrid.Cells[ GrdCol, GrdRow ] := NODEJOINT
      end
      else
      begin
        // Check if a start or end point is being erased
        if MazeGrid.Cells[ GrdCol, GrdRow ] = NODESTART then
        begin
          StartPt.X  := -1;
          StartPt.Y  := -1;
        end
        else if MazeGrid.Cells[ GrdCol, GrdRow ] = NODEEND then
        begin
          EndPt.X    := -1;
          EndPt.Y    := -1;
        end;
        // Clear the cell
        MazeGrid.Cells[ GrdCol, GrdRow ] := NODECLEAR;
      end;
    end;
  end;
end;

procedure TMazeForm.MazeGridMouseUp(Sender: TObject; Button: TMouseButton;
  Shift: TShiftState; X, Y: Integer);
begin
  Dragging := FALSE;
end;

procedure TMazeForm.BrushShapeMouseDown(Sender: TObject;
  Button: TMouseButton; Shift: TShiftState; X, Y: Integer);
begin
  if ( PenType < PENSTART ) then
    LastPenType := PenType;
  PenType := TShape(Sender).Tag;
  ShapeCurrent.Brush.Color := TShape(Sender).Brush.Color;
end;

procedure TMazeForm.MazeGridDrawCell(Sender: TObject; ACol, ARow: Integer;
  Rect: TRect; State: TGridDrawState);
begin
  MazeGrid.Canvas.Brush.Color := clWhite;

  if MazeGrid.Cells[ACol, ARow] = NODESTART then
    MazeGrid.Canvas.Brush.Color := clBlue
  else if MazeGrid.Cells[ACol, ARow] = NODEEND then
    MazeGrid.Canvas.Brush.Color := clRed
  else if MazeGrid.Cells[ACol, ARow] = NODEPATH1 then
    MazeGrid.Canvas.Brush.Color := clFuchsia
  else if MazeGrid.Cells[ACol, ARow] = NODEPATH2 then
    MazeGrid.Canvas.Brush.Color := clPurple
  else if MazeGrid.Cells[ACol, ARow] = NODEJOINT then
    MazeGrid.Canvas.Brush.Color := clGreen;

  MazeGrid.Canvas.FillRect(Rect);
end;

procedure TMazeForm.New1Click(Sender: TObject);
var
  iCount, iCount2: Integer;
begin
  // Clear out the map
  for iCount := 0 to 49 do
    for iCount2 := 0 to 49 do
      MazeGrid.Cells[ iCount, iCount2 ] := '';
  // Reset the environment
  PenType := PENPATH1;
  ShapeCurrent.Brush.Color := clFuchsia;
end;

procedure TMazeForm.Close1Click(Sender: TObject);
var
  OutputFile : TextFile;
  x, y, width, height, BlockNr : Integer;
begin
  if ( SaveDialog.Execute ) then
  begin
    // Create file
    AssignFile( OutputFile, SaveDialog.FileName );
    ReWrite( OutputFile );

    // Write header
    WriteHeader( OutputFile );

    // Write block information
    width   := MazeGrid.ColCount;
    height  := MazeGrid.RowCount;
    BlockNr := 0;
    WriteLn( OutputFile, '\\ Block Information' );
    for y := 0 to height -1 do
      for x := 0 to width -1 do
      begin
        if ( MazeGrid.Cells[ x, y ] = NODEPATH1 ) OR
           ( MazeGrid.Cells[ x, y ] = NODEPATH2 ) OR
           ( MazeGrid.Cells[ x, y ] = NODEJOINT ) then
        begin
          WriteLn( OutputFile, 'BLOCK' + IntToStr( BlockNr ) + ' = ' +
            IntToStr( x ) + ' ' + IntToStr( y ) + ' ' + getType( x, y )
            + ' ' + MazeGrid.Cells[ x, y ] );
          Inc( BlockNr );
        end;
      end;

    // Close file
    CloseFile( OutputFile );
  end;
end;

procedure TMazeForm.WriteHeader( var OutputFile: TextFile );
var
  upper, left, width, height, x, y: Integer;
begin
  upper  := MazeGrid.Height;
  left   := MazeGrid.Width;
  width  := -1;
  height := -1;

  // Calculate size and offset
  for y := 0 to MazeGrid.Height -1 do
    for x := 0 to MazeGrid.Width -1 do
    begin
      if ( MazeGrid.Cells[ x, y ] <> NODECLEAR ) then
      begin
        if ( x < left )   then left   := x;
        if ( y < upper )  then upper  := y;
        if ( x > width )  then width  := x;
        if ( y > height ) then height := y;
      end;
    end;
  Dec( width, left );
  Dec( height, upper );

  // Write Header
  WriteLn( OutputFile, '\\ aMAZEing Labyrinth Level File' );
  WriteLn( OutputFile, 'OFFSET = ' + IntToStr( left ) + ' ' + IntToStr( upper ) );
  WriteLn( OutputFile, 'SIZE = ' + IntToStr( width + 1 ) + ' ' + IntToStr( height + 1 ) );
  WriteLn( OutputFile, 'START = ' + IntToStr( StartPt.X ) + ' ' + IntToStr( StartPt.Y )
    + ' ' + getType( StartPt.X, StartPt.Y ) + ' ' + MazeGrid.Cells[ StartPt.X, StartPt.Y ] );
  WriteLn( OutputFile, 'END = ' + IntToStr( EndPt.X ) + ' ' + IntToStr( EndPt.Y )
    + ' ' + getType( EndPt.X, EndPt.Y ) + ' ' + MazeGrid.Cells[ EndPt.X, EndPt.Y ] );
  WriteLn( OutputFile, '' );
end;

procedure TMazeForm.Open1Click(Sender: TObject);
var
  InputFile: TextFile;
  line: String;
  i, x, y, color: Integer;
begin
  if ( OpenDialog.Execute ) then
  begin
    New1Click( Self );
    try
      AssignFile( InputFile, OpenDialog.FileName );
      Reset(InputFile);

      // Skip header
      for i := 1 to 3 do
        ReadLn( InputFile, line );

      // Read start position
      ReadLn( InputFile, line );
      x := getValue( line, 2 );
      y := getValue( line, 3 );
      color := getValue( line, 6 );
      StartPt.X := x;
      StartPt.Y := y;
      MazeGrid.Cells[ x, y ] := IntToStr(color);

      // Read end position
      ReadLn( InputFile, line );
      x := getValue( line, 2 );
      y := getValue( line, 3 );
      color := getValue( line, 6 );
      EndPt.X := x;
      EndPt.Y := y;
      MazeGrid.Cells[ x, y ] := IntToStr(color);

      // Skip header
      for i := 1 to 2 do
        ReadLn( InputFile, line );

      // Read block info
      while not SeekEof( InputFile ) do
      begin
        ReadLn( InputFile, line );
        x := getValue( line, 2 );
        y := getValue( line, 3 );
        color := getValue( line, 6 );
        MazeGrid.Cells[ x, y ] := IntToStr(color);
      end;
      CloseFile ( InputFile );

      // Reset the environment
      PenType := PENPATH1;
      ShapeCurrent.Brush.Color := clFuchsia;
    except
      New1Click( Self );
      ShowMessage( 'Invalid Level File' );
      CloseFile ( InputFile );
    end;
  end;
end;

function TMazeForm.getValue( s: String; index: Integer ): Integer;
var
  value: String;
  i, level: Integer;
begin
  level := 0;
  value := '';

  for i := 1 to Length( s ) do
  begin
    if ( s[ i ] = ' ' ) then
      if ( level = index ) then
      begin
        Result := StrToInt( value );
        exit;
      end
      else
      begin
        value := '';
        Inc( level );
      end
    else
      value := value + s[ i ];
  end;
  Result := StrToInt( value );
end;

function TMazeForm.getType( x: Integer; y: Integer ): String;
var
  block: Integer;
  blocktype: String;
begin
  block := 0;

  // Check if coordinates exist
  if ( ( x = -1 ) OR ( y = -1 ) ) then
  begin
    Result := 'EMPTY 0'; // ???
    exit;
  end;

  // Check current block
  blocktype := MazeGrid.Cells[ x, y ];

  // Calculate block value
  if ( blocktype = NODEPATH1 ) then
  begin
    if ( ( y + 1 < MazeGrid.RowCount ) AND
       ( ( MazeGrid.Cells[ x, y + 1 ] <> NODECLEAR ) AND
         ( MazeGrid.Cells[ x, y + 1 ] <> NODEPATH2 ))) then
      Inc( block, 1 );
    if ( ( x + 1 < MazeGrid.ColCount ) AND
       ( ( MazeGrid.Cells[ x + 1, y ] <> NODECLEAR ) AND
         ( MazeGrid.Cells[ x + 1, y ] <> NODEPATH2 ))) then
      Inc( block, 2 );
    if ( ( y - 1 >= 0  ) AND
       ( ( MazeGrid.Cells[ x, y - 1 ] <> NODECLEAR ) AND
         ( MazeGrid.Cells[ x, y - 1 ] <> NODEPATH2 ))) then
      Inc( block, 4 );
    if ( ( x - 1 >= 0  ) AND
       ( ( MazeGrid.Cells[ x - 1, y ] <> NODECLEAR ) AND
         ( MazeGrid.Cells[ x - 1, y ] <> NODEPATH2 ))) then
      Inc( block, 8 );
  end
  else if ( blocktype = NODEPATH2 ) then
  begin
    if ( ( y + 1 < MazeGrid.RowCount ) AND
       ( ( MazeGrid.Cells[ x, y + 1 ] <> NODECLEAR ) AND
         ( MazeGrid.Cells[ x, y + 1 ] <> NODEPATH1 ))) then
      Inc( block, 1 );
    if ( ( x + 1 < MazeGrid.ColCount ) AND
       ( ( MazeGrid.Cells[ x + 1, y ] <> NODECLEAR ) AND
         ( MazeGrid.Cells[ x + 1, y ] <> NODEPATH1 ))) then
      Inc( block, 2 );
    if ( ( y - 1 >= 0  ) AND
       ( ( MazeGrid.Cells[ x, y - 1 ] <> NODECLEAR ) AND
         ( MazeGrid.Cells[ x, y - 1 ] <> NODEPATH1 ))) then
      Inc( block, 4 );
    if ( ( x - 1 >= 0  ) AND
       ( ( MazeGrid.Cells[ x - 1, y ] <> NODECLEAR ) AND
         ( MazeGrid.Cells[ x - 1, y ] <> NODEPATH1 ))) then
      Inc( block, 8 );
  end
  else
  begin
    if ( ( y + 1 < MazeGrid.RowCount ) AND ( MazeGrid.Cells[ x, y + 1 ] <> NODECLEAR ) ) then
      Inc( block, 1 );
    if ( ( x + 1 < MazeGrid.ColCount ) AND ( MazeGrid.Cells[ x + 1, y ] <> NODECLEAR ) ) then
      Inc( block, 2 );
    if ( ( y - 1 >= 0  ) AND ( MazeGrid.Cells[ x, y - 1 ] <> NODECLEAR ) ) then
      Inc( block, 4 );
    if ( ( x - 1 >= 0  ) AND ( MazeGrid.Cells[ x - 1, y ] <> NODECLEAR ) ) then
      Inc( block, 8 );
  end;

  // Return correct block
  case block of
    0:
      Result := 'EMPTY 0'; // ???
    1:
      Result := 'DEADEND 0'; // ???
    2:
      Result := 'DEADEND 90'; // ???
    3:
      Result := 'CORNER 0';
    4:
      Result := 'DEADEND 180'; // ???
    5:
      Result := 'STRAIGHT 90';
    6:
      Result := 'CORNER 90';
    7:
      Result := 'T-SPLIT 90';
    8:
      Result := 'DEADEND 270'; // ???
    9:
      Result := 'CORNER 270';
    10:
      Result := 'STRAIGHT 0';
    11:
      Result := 'T-SPLIT 0';
    12:
      Result := 'CORNER 180';
    13:
      Result := 'T-SPLIT 270';
    14:
      Result := 'T-SPLIT 180';
    15:
      Result := 'CROSSING 0';
  end;
end;

procedure TMazeForm.Exit1Click(Sender: TObject);
begin
  Close;
end;

procedure TMazeForm.RadioGroup1Click(Sender: TObject);
begin
  with MazeGrid do
  begin
    if RadioGroup1.ItemIndex = 0 then
    begin
      ColCount := 25;
      RowCount := 25;
      DefaultColWidth := 21;
      DefaultRowHeight := 21;
    end
    else
    begin
      ColCount := 50;
      RowCount := 50;
      DefaultColWidth := 10;
      DefaultRowHeight := 10;
    end;
  end;
end;

end.
