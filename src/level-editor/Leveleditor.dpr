program Leveleditor;

uses
  Forms,
  MazeUnit in 'MazeUnit.pas' {MazeForm};

{$R *.res}

begin
  Application.Initialize;
  Application.CreateForm(TMazeForm, MazeForm);
  Application.Run;
end.
