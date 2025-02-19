Graphics 800,600,32,2

;Create images for planet and particles
Global planet=CreateImage(128,128)
Global particle=CreateImage(1,1)

MidHandle planet
MidHandle particle


Text 0,0,"Loading..."
;Planet zeichnen
createplanet()

;Partikel zeichnen
createparticle()

Text 0,12,"...done"
Delay 1000

Cls

;Particle Typ
Type partikel

        Field x#
        Field y#
        Field xg#
        Field yg#
       
End Type

Global curr.partikel

Global mousetimer=MilliSecs()

Global phy=MilliSecs()

FPS=0
fpst=MilliSecs()
fpsc=0

Const mass#=0.65
Const pmass#=0.0075

SetBuffer BackBuffer()

Repeat



        ;Zeichnen
        addparticle()
        drawparticle()
        DrawImage planet,399,299
        DrawImage particle,MouseX(),MouseY()
       
       
        ;FPS
        Text 0,0,"FPS: "+fps

                If MilliSecs()-fpst=>1000 Then
               
                        fps=fpsc
                        fpsc=0
                        fpst=MilliSecs()
                               
                End If

        fpsc=fpsc+1     
       
        ;Berechnung
        velocity()
        particlecollide()

        ;Pageflipping
        Flip 0
        Cls

Until KeyHit(1)





Function addparticle()


        If MilliSecs()-mousetimer=>10 Then
       
                If MouseDown(1) Then


                        curr.partikel=New partikel
                       
                        curr\x#=MouseX()
                        curr\y#=MouseY()
                        curr\xg#=0
                        curr\yg#=0


                End If
               
                mousetimer=MilliSecs()

        End If


End Function


Function drawparticle()

        For curr.partikel=Each partikel


                DrawImage particle,Int(curr\x#),Int(curr\y#)
               

        Next

End Function


Function createplanet()

        ;Imagebuffer für Writepixelfast setzen
        SetBuffer ImageBuffer(planet)
        LockBuffer ImageBuffer(planet)

                               
                For rad=0 To 63
               
                        For deg#=0 To 360 Step 0.01
                       
                                       
                                ;Farbwerte berechnen
                               
                                g=Rand(100,255)
                                       
                                rgb=a*$1000000 + g*$10000 + g*$100 + g

                                WritePixelFast 63+(Cos(deg#)*rad),63+(Sin(deg#)*rad),rgb

                        Next

                Next


        UnlockBuffer ImageBuffer(planet)
       
        SetBuffer FrontBuffer()

End Function


Function createparticle()


        SetBuffer ImageBuffer(particle)

                SeedRnd MilliSecs()
               
                r=Rand(50,255)
                g=Rand(50,255)
                b=Rand(50,255)

               
                rgb=a*$1000000 + r*$10000 + g*$100 + b
               
                WritePixel 0,0,rgb

               
        SetBuffer FrontBuffer()
       

End Function

Function velocity()

        If MilliSecs()-phy=>50 Then

        For curr.partikel= Each partikel
       
                xdist#=399.0-curr\x#
                ydist#=299.0-curr\y#
       
                curr\xg#=curr\xg#+xdist#/10000.0
                curr\yg#=curr\yg#+ydist#/10000.0
               
                curr\x#=curr\x#+curr\xg#
                curr\y#=curr\y#+curr\yg#

        Next

        phy=MilliSecs()

        End If

End Function


Function particlecollide()


        For curr.partikel = Each partikel
       
               
                If ImagesCollide(particle,curr\x#,curr\y#,0,planet,399,299,0) Then
               
                        curr\xg#=-curr\xg#*0.999
                        curr\yg#=-curr\yg#*0.999
               
                End If
                       

        Next

End Function 
;~IDEal Editor Parameters:
;~C#Blitz3D
