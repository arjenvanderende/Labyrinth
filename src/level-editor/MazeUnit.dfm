object MazeForm: TMazeForm
  Left = 210
  Top = 56
  BorderIcons = [biSystemMenu, biMinimize]
  BorderStyle = bsSingle
  Caption = 'aMAZEing Labyrinth - Leveleditor'
  ClientHeight = 594
  ClientWidth = 720
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  Menu = MainMenu
  OldCreateOrder = False
  OnCreate = FormCreate
  PixelsPerInch = 96
  TextHeight = 13
  object GroupBox1: TGroupBox
    Left = 8
    Top = 8
    Width = 569
    Height = 577
    Caption = ' Maze '
    TabOrder = 0
    object MazeGrid: TStringGrid
      Left = 7
      Top = 16
      Width = 553
      Height = 553
      ColCount = 25
      DefaultColWidth = 21
      DefaultRowHeight = 21
      DefaultDrawing = False
      FixedCols = 0
      RowCount = 25
      FixedRows = 0
      ScrollBars = ssNone
      TabOrder = 0
      OnDrawCell = MazeGridDrawCell
      OnMouseDown = MazeGridMouseDown
      OnMouseMove = MazeGridMouseMove
      OnMouseUp = MazeGridMouseUp
    end
  end
  object GroupBox2: TGroupBox
    Left = 584
    Top = 88
    Width = 129
    Height = 193
    Caption = ' Brush Type '
    TabOrder = 1
    object StartShape: TShape
      Tag = 3
      Left = 16
      Top = 24
      Width = 25
      Height = 25
      Brush.Color = clBlue
      OnMouseDown = BrushShapeMouseDown
    end
    object EndShape: TShape
      Tag = 4
      Left = 16
      Top = 56
      Width = 25
      Height = 25
      Brush.Color = clRed
      OnMouseDown = BrushShapeMouseDown
    end
    object Label1: TLabel
      Left = 56
      Top = 32
      Width = 49
      Height = 13
      Caption = 'Start Point'
    end
    object Label2: TLabel
      Left = 56
      Top = 64
      Width = 46
      Height = 13
      Caption = 'End Point'
    end
    object Path1Shape: TShape
      Left = 16
      Top = 88
      Width = 25
      Height = 25
      Brush.Color = clFuchsia
      OnMouseDown = BrushShapeMouseDown
    end
    object Path2Shape: TShape
      Tag = 1
      Left = 16
      Top = 120
      Width = 25
      Height = 25
      Brush.Color = clPurple
      OnMouseDown = BrushShapeMouseDown
    end
    object JoinShape: TShape
      Tag = 2
      Left = 16
      Top = 152
      Width = 25
      Height = 25
      Brush.Color = clGreen
      OnMouseDown = BrushShapeMouseDown
    end
    object Label3: TLabel
      Left = 56
      Top = 96
      Width = 31
      Height = 13
      Caption = 'Path 1'
    end
    object Label4: TLabel
      Left = 56
      Top = 128
      Width = 31
      Height = 13
      Caption = 'Path 2'
    end
    object Label5: TLabel
      Left = 56
      Top = 160
      Width = 47
      Height = 13
      Caption = 'Path Joint'
    end
  end
  object RadioGroup1: TRadioGroup
    Left = 584
    Top = 8
    Width = 129
    Height = 73
    Caption = ' Grid Size '
    ItemIndex = 0
    Items.Strings = (
      '25 x 25'
      '50 x 50')
    TabOrder = 2
    OnClick = RadioGroup1Click
  end
  object GroupBox3: TGroupBox
    Left = 584
    Top = 288
    Width = 129
    Height = 65
    Caption = ' Current Brush '
    TabOrder = 3
    object ShapeCurrent: TShape
      Left = 48
      Top = 24
      Width = 25
      Height = 25
      Brush.Color = clFuchsia
    end
  end
  object MainMenu: TMainMenu
    BiDiMode = bdLeftToRight
    ParentBiDiMode = False
    Left = 680
    Top = 488
    object File1: TMenuItem
      Caption = '&File'
      object New1: TMenuItem
        Caption = '&New'
        ShortCut = 16462
        OnClick = New1Click
      end
      object Open1: TMenuItem
        Caption = '&Open...'
        ShortCut = 16463
        OnClick = Open1Click
      end
      object Close1: TMenuItem
        Caption = '&Save'
        ShortCut = 16467
        OnClick = Close1Click
      end
      object N1: TMenuItem
        Caption = '-'
      end
      object Exit1: TMenuItem
        Caption = 'E&xit'
        OnClick = Exit1Click
      end
    end
  end
  object OpenDialog: TOpenDialog
    DefaultExt = '*.mze'
    FileName = 
      'D:\Software Development\Delphi 7\Projects\aMAZEing Labyrinth\lev' +
      'el2.mze'
    Filter = 'aMAZEing Labyrinth Level File (*.mze)|*.mze'
    Options = [ofHideReadOnly, ofPathMustExist, ofFileMustExist, ofEnableSizing]
    Left = 680
    Top = 520
  end
  object SaveDialog: TSaveDialog
    DefaultExt = '.mze'
    Filter = 'aMAZEing Labyrinth Level File (*.mze)|*.mze'
    InitialDir = '.'
    Options = [ofOverwritePrompt, ofHideReadOnly, ofEnableSizing]
    Left = 680
    Top = 552
  end
end
